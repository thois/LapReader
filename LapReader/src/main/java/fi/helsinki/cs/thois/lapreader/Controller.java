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
    
    /**
     * Time formatter
     */
    DateFormat tf = new SimpleDateFormat("HH:mm");
    
    /**
     * date formatter
     */
    DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
    
    /**
     * Constructor, that creates all daos and databases
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
    
    /**
     * Puts foreign keys on with SQLite to get cascade on delete working
     * @throws SQLException when database error occurs
     */
    public void foreigKeysOn() throws SQLException {
        testDayDao.executeRaw("PRAGMA foreign_keys = true;");
        heatDao.executeRaw("PRAGMA foreign_keys = true;");
        lapDao.executeRaw("PRAGMA foreign_keys = true;");
        resultDao.executeRaw("PRAGMA foreign_keys = true;");
        createTablesIfNotExists();
    }
    
    /**
     * Drops all tables. For easier testability.
     * @throws SQLException when database error occurs
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
     * Get all days from database
     * @return all days in list
     * @throws SQLException 
     */
    public List<TestDay> getDays() throws SQLException {
        QueryBuilder<TestDay, String> queryBuilder = testDayDao.queryBuilder();
        return queryBuilder.orderBy("day", true).query();
    }
    
    
    /**
     * Adds new day to database
     * @param date as a dd.MM.yyyy formatted string
     * @return reference to created day
     * @throws ParseException if parsing day from string fails
     * @throws SQLException if database operation fails
     */
    public TestDay addDay(String date) throws ParseException, SQLException {
        TestDay d;
        if (date == null || date.isEmpty())
            d = new TestDay();
        else
            d = new TestDay(df.parse(date));
        testDayDao.create(d);
        return d;
    }
    
    /**
     * Changes TestDate's date
     * @param day TestDay to be modified
     * @param date new date as a dd.MM.yyyy formatted string
     * @throws SQLException when database error occurs
     * @throws ParseException when new date cannot be parsed
     */
    public void updateDay(TestDay day, String date) throws SQLException, ParseException {
        day.setDay(df.parse(date));
        testDayDao.update(day);
        testDayDao.refresh(day);
    }
    
    /**
     * Deletes a day from database
     * @param day to be deleted
     * @throws SQLException when database error occurs
     */
    public void deleteDay(TestDay day) throws SQLException {
        testDayDao.refresh(day);
        ForeignCollection<Heat> heats = day.getHeats();
        if (heats != null)
            for (Heat h : heats) {
                deleteHeat(h);
            }
        testDayDao.delete(day);
    }
    
    /**
     * Inserts Heat to database and reads laptimes from file
     * @param day as a TestDay object already persisted in the database
     * @param filename  as a string
     * @param time of the day as a string
     * @param parser to be used for parsing laptimes
     * @return created Heat
     * @throws IOException if file opening fails
     * @throws ParseException if parsing time or file fails
     * @throws SQLException  if database operation fails
     */
    public Heat addHeatFromFile(TestDay day, String filename, String time,
            Parser parser)
            throws IOException, ParseException, SQLException {
        return addHeatFromFile(day, Paths.get(filename), time, parser);
    }
    
    /**
     * Inserts Heat to database and reads laptimes from file
     * @param day as a TestDay object already persisted in the database
     * @param path to file to be read
     * @param time of the day as a string
     * @param parser to be used for parsing laptimes
     * @return created Heat
     * @throws IOException if file opening fails
     * @throws ParseException if parsing time or file fails
     * @throws SQLException  if database operation fails
     */
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
     * @param parser to be used for parsing laptimes
     * @return created Heat
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
    
    /**
     * Updates time of a heat
     * @param heat to be modified
     * @param time new time as a HH:mm formatted string
     * @throws SQLException when database error occurs
     * @throws ParseException when parsing new time fails
     */
    public void updateHeat(Heat heat, String time) throws SQLException, ParseException {
        heat.setTime(tf.parse(time));
        updateHeat(heat);
    }
    
    /**
     * Updates modified heat to database and refresh it
     * @param heat to be updated
     * @throws SQLException when database error occurs
     */
    public void updateHeat(Heat heat) throws SQLException {
        heatDao.update(heat);
        heatDao.refresh(heat);
    }
    
    /**
     * Deletes a heat from database
     * @param heat to be deleted
     * @throws SQLException when database error occurs
     */
    public void deleteHeat(Heat heat) throws SQLException {
        heatDao.refresh(heat);
        resultDao.delete(heat.getResult());
        heatDao.delete(heat);
    }
    
   /**
     * Get all heats connected to a day from database
     * @param day to be searched
     * @return found heats as ForeignCollection
     * @throws SQLException if database operations fails
     */
    public ForeignCollection<Heat> getHeats(TestDay day) throws SQLException {
        testDayDao.refresh(day);
        return day.getHeats();
    }
    
    /**
     * Get laptimes from a heat
     * @param heat as a object persisted in database
     * @return laptimes as a ForeignCollection
     * @throws SQLException if database operations fails
     */
    public ForeignCollection<Lap> getLaps(Heat heat) throws SQLException {
        heatDao.refresh(heat);
        return heat.getLaps();
    }
    
    /**
     * Gets best lap archieved in a heat
     * @param heat to be looked
     * @return best lap
     * @throws SQLException if database operations fails
     */
    public Lap getBestLap(Heat heat) throws SQLException {
        QueryBuilder<Lap, String> qb =
                lapDao.queryBuilder().orderBy("time", true);
        return qb.where().eq("heat_id", heat.getId()).queryForFirst();
    }
    
    /**
     * Gets best lap archieved in a day
     * @param day to be looked
     * @return best lap
     * @throws SQLException if database operations fails
     */
    public Lap getBestLap(TestDay day) throws SQLException {
        QueryBuilder<Lap, String> qb =
                lapDao.queryBuilder().orderBy("time", true);
        QueryBuilder<Heat, String> heatQb = heatDao.queryBuilder();
        heatQb.where().eq("testday_id", day.getId());
        return qb.join(heatQb).queryForFirst();
    }
    
    /**
     * Gets best result archieved in a day
     * @param day to be looked
     * @return best result
     * @throws SQLException if database operations fails
     */
    public Result getBestResult(TestDay day) throws SQLException {
        QueryBuilder<Result, String> qb = resultDao.queryBuilder().
                orderBy("laps", false).orderBy("time", true);
        QueryBuilder<Heat, String> heatQb = heatDao.queryBuilder();
        heatQb.where().eq("testday_id", day.getId());
        return qb.join(heatQb).queryForFirst();
    }
    
    /**
     * Gets a Heat by known result from db
     * @param result which parent is needed
     * @return heat if found
     * @throws SQLException if database operations fails
     */
    public Heat getHeatByResult(Result result) throws SQLException {
        QueryBuilder<Heat, String> heatQb = heatDao.queryBuilder();
        return heatQb.where().eq("result_id", result.getId()).queryForFirst();
    }
    
    /**
     * Gets a TestDay by known id. Mainly for testing.
     * @param id to be looked from db
     * @return TestDay if found
     * @throws SQLException if database operations fails
     */
    public TestDay getTestDayById(int id) throws SQLException {
        return testDayDao.queryForId(""+id);
    }
    
    /**
     * Gets a Heat by known id. Mainly for testing.
     * @param id to be looked from db
     * @return Heat if found
     * @throws SQLException if database operations fails
     */
    public Heat getHeatById(int id) throws SQLException {
        return heatDao.queryForId(""+id);
    }
    
    /**
     * Gets a result by known id. Mainly for testing.
     * @param id to be looked from db
     * @return Result if found
     * @throws SQLException if database operations fails
     */
    public Result getResultById(int id) throws SQLException {
        return resultDao.queryForId(""+id);
    }
}
