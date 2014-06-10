/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.thois.lapreader;

import com.j256.ormlite.dao.*;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import fi.helsinki.cs.thois.lapreader.model.*;
import fi.helsinki.cs.thois.lapreader.parser.OrionParser;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Class includes main program logic
 */
public class Controller {
    
    ConnectionSource connectionSource;
    Dao<TestDay, String> testDayDao;
    Dao<Heat, String> heatDao;
    Dao<Lap, String> lapDao;
    Dao<Result, String> resultDao;
    
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
    }
    
    /**
     * Creates all tables to databases, if they don't exist
     * @throws SQLException 
     */
    private void createTablesIfNotExists() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, TestDay.class);
        TableUtils.createTableIfNotExists(connectionSource, Heat.class);
        TableUtils.createTableIfNotExists(connectionSource, Lap.class);
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
        TestDay d = new TestDay(date);
        testDayDao.create(d);
        return d;
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
    public void addHeatFromFile(TestDay day, String filename, String time)
            throws IOException, ParseException, SQLException {
        List<String> lines = Files.readAllLines(Paths.get(filename),
                StandardCharsets.UTF_8);
        addHeat(day, lines.toArray(new String[0]), time);
    }
    
    /**
     * Inserts Heat including laptimes to database
     * @param day as a TestDay object already persisted in the database
     * @param lines laptimes in array of strings
     * @param time of the day as a string
     * @throws ParseException if parsing time or laptimes fails
     * @throws SQLException if database operation fails
     */
    public void addHeat(TestDay day, String[] lines, String time) throws
            ParseException, SQLException {
        DateFormat df = new SimpleDateFormat("HH:mm");
        Heat h;
        if (time.isEmpty()||time == null)
            h = new Heat(null, day);
        else
            h = new Heat(df.parse(time), day);
        List<Integer> laps = OrionParser.parse(lines);
        heatDao.create(h);
        for (int i = 0; i < laps.size(); i++) {
            Lap l = new Lap(laps.get(i), i+1, h);
            lapDao.create(l);
        }
        Result result = new Result(laps, h);
        resultDao.create(result);
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
