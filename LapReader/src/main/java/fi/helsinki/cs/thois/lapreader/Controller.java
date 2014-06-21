package fi.helsinki.cs.thois.lapreader;

import com.j256.ormlite.dao.*;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import fi.helsinki.cs.thois.lapreader.model.*;
import fi.helsinki.cs.thois.lapreader.parser.Parser;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.sql.SQLException;
import java.text.*;
import java.util.List;

/**
 * Class includes main program logic
 */
public class Controller {
    
    /**
     * Database connection source for using the database
     */
    ConnectionSource connectionSource;
    
    /**
     * Daos for persisting the models
     */
    Dao<TestDay, String> testDayDao;
    Dao<Heat, String> heatDao;
    Dao<Lap, String> lapDao;
    Dao<Result, String> resultDao;
    
    DateFormat tf = new SimpleDateFormat("HH:mm");
    DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
    
    /**
     * Consturctor, that creates all daos and databases
     * @param databaseUrl as a string in persistence lib format
     * @throws SQLException 
     */
    public Controller(String databaseUrl) throws SQLException {
        
        connectionSource =
            new JdbcConnectionSource(databaseUrl);
        testDayDao =
            DaoManager.createDao(connectionSource, TestDay.class);
        heatDao =
            DaoManager.createDao(connectionSource, Heat.class);
        lapDao =
            DaoManager.createDao(connectionSource, Lap.class);
        resultDao =
            DaoManager.createDao(connectionSource, Result.class);

    }
    
    public void foreigKeysOn() throws SQLException {
        testDayDao.executeRaw("PRAGMA foreign_keys = true;");
        heatDao.executeRaw("PRAGMA foreign_keys = true;");
        lapDao.executeRaw("PRAGMA foreign_keys = true;");
        resultDao.executeRaw("PRAGMA foreign_keys = true;");
        createTablesIfNotExists();
    }
    
    /**
     * Drops all tables. For easier testability.
     * @throws SQLException 
     */
    public void dropTables() throws SQLException {
        TableUtils.dropTable(connectionSource, TestDay.class, true);
        TableUtils.dropTable(connectionSource, Heat.class, true);
        TableUtils.dropTable(connectionSource, Lap.class, true);
        TableUtils.dropTable(connectionSource, Result.class, true);
    }
    
    /**
     * Creates all tables to databases, if they don't exist
     * @throws SQLException 
     */
    private void createTablesIfNotExists() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, TestDay.class);
        TableUtils.createTableIfNotExists(connectionSource, Heat.class);
        TableUtils.createTableIfNotExists(connectionSource, Lap.class);
        TableUtils.createTableIfNotExists(connectionSource, Result.class);
    }
    
    /**
     * Get days from database
     * @return all days in list
     * @throws SQLException 
     */
    public List<TestDay> getDays() throws SQLException {
        QueryBuilder<TestDay, String> queryBuilder = testDayDao.queryBuilder();
        return queryBuilder.orderBy("day", true).query();
    }
    
    
    /**
     * Adds new day to database
     * @param date as a string "dd.mm.yyyy"
     * @return reference to created day
     * @throws ParseException if parsing day from string fails
     * @throws SQLException if database operation fails
     */
    public TestDay addDay(String date) throws ParseException, SQLException {
        TestDay d;
        if (date == null)
            d = new TestDay();
        else
            d = new TestDay(df.parse(date));
        testDayDao.create(d);
        return d;
    }
    
    public void updateDay(TestDay day, String date) throws SQLException, ParseException {
        day.setDay(df.parse(date));
        testDayDao.update(day);
        testDayDao.refresh(day);
    }
    
    public void deleteDay(TestDay day) throws SQLException {
        testDayDao.delete(day);
    }
    
    /**
     * Inserts Heat to database and reads laptimes from file
     * @param day as a TestDay object already persisted in the database
     * @param filename  as a string
     * @param time of the day as a string
     * @throws IOException if file opening fails
     * @throws ParseException if parsing time or file fails
     * @throws SQLException  if database operation fails
     */
    public Heat addHeatFromFile(TestDay day, String filename, String time,
            Parser parser)
            throws IOException, ParseException, SQLException {
        return addHeatFromFile(day, Paths.get(filename), time, parser);
    }
    
    public Heat addHeatFromFile(TestDay day, Path path, String time,
            Parser parser) throws IOException, ParseException, SQLException {
        List<String> lines = Files.readAllLines(path,
                StandardCharsets.UTF_8);
        return addHeat(day, lines.toArray(new String[0]), time, parser);
    }
    
    /**
     * Inserts Heat including laptimes to database
     * @param day as a TestDay object already persisted in the database
     * @param lines laptimes in array of strings
     * @param time of the day as a string
     * @throws ParseException if parsing time or laptimes fails
     * @throws SQLException if database operation fails
     */
    public Heat addHeat(TestDay day, String[] lines, String time, Parser parser) throws
            ParseException, SQLException {
        Heat h;
        if (time == null||time.isEmpty())
            h = new Heat(null, day);
        else
            h = new Heat(tf.parse(time), day);
        List<Integer> laps = parser.parse(lines);
        heatDao.create(h);
        for (int i = 0; i < laps.size(); i++) {
            Lap l = new Lap(laps.get(i), i+1, h);
            lapDao.create(l);
        }
        Result result = new Result(laps);
        resultDao.create(result);
        h.setResult(result);
        heatDao.update(h);
        heatDao.refresh(h);
        return h;
    }
    
    public void updateHeat(Heat heat, String time) throws SQLException, ParseException {
        heat.setTime(tf.parse(time));
        heatDao.update(heat);
        heatDao.refresh(heat);
    }
    
    public void deleteHeat(Heat heat) throws SQLException {
        heatDao.delete(heat);
    }
    
   /**
     * Get all heats connected to day from database
     * @param day to be searched
     * @return found heats as ForeignCollection
     * @throws SQLException if database operations fails
     */
    public ForeignCollection<Heat> getHeats(TestDay day) throws SQLException {
        testDayDao.refresh(day);
        return day.getHeats();
    }
    
    /**
     * Get laptimes from the heat
     * @param heat as a object persisted in database
     * @return laptimes as a ForeignCollection
     * @throws SQLException if database operations fails
     */
    public ForeignCollection<Lap> getLaps(Heat heat) throws SQLException {
        heatDao.refresh(heat);
        return heat.getLaps();
    }
    
}
