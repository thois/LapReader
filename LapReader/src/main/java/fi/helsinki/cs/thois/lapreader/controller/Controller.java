/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.thois.lapreader.controller;

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
 *
 * @author niko
 */
public class Controller {
    
    ConnectionSource connectionSource;
    Dao<TestDay, String> testDayDao;
    Dao<Heat, String> heatDao;
    Dao<Lap, String> lapDao;
    
    public Controller(String databaseUrl) throws SQLException {
        
        connectionSource =
            new JdbcConnectionSource(databaseUrl);
        
        testDayDao =
            DaoManager.createDao(connectionSource, TestDay.class);
        heatDao =
            DaoManager.createDao(connectionSource, Heat.class);
        lapDao =
            DaoManager.createDao(connectionSource, Lap.class);
        TableUtils.createTableIfNotExists(connectionSource, TestDay.class);
        TableUtils.createTableIfNotExists(connectionSource, Heat.class);
        TableUtils.createTableIfNotExists(connectionSource, Lap.class);
    }
    
    public Dao<TestDay, String> getTestDayDao() {
        return testDayDao;
    }
    
    public List<TestDay> getDays() throws SQLException {
        QueryBuilder<TestDay, String> queryBuilder = testDayDao.queryBuilder();
        return queryBuilder.orderBy("day", true).query();
    }
    
    public void addDay(String date) throws ParseException, SQLException {
        TestDay d = new TestDay(date);
        testDayDao.create(d);
    }
    
    public void addHeat(TestDay day, String filename, String time) throws ParseException, IOException, SQLException {
        DateFormat df = new SimpleDateFormat("HH.mm");
        Heat h;
        if (time.isEmpty()||time == null)
            h = new Heat();
        else
            h = new Heat(df.parse(time));
        List<String> lines = Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);
        List<Integer> laps = OrionParser.parse(lines.toArray(new String[lines.size()]));
        for (Integer lap: laps) {
            Lap l = new Lap(lap);
            lapDao.create(l);
        }
        day.addHeat(h);
        heatDao.update(h);
        testDayDao.update(day);
    }
    
}
