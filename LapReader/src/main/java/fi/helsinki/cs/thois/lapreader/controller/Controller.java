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
import fi.helsinki.cs.thois.lapreader.model.TestDay;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author niko
 */
public class Controller {
    
    ConnectionSource connectionSource;
    Dao<TestDay, String> testDayDao;
    
    public Controller(String databaseUrl) throws SQLException {
        
        connectionSource =
            new JdbcConnectionSource(databaseUrl);
        
        testDayDao =
            DaoManager.createDao(connectionSource, TestDay.class);
        
        TableUtils.createTableIfNotExists(connectionSource, TestDay.class);
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
    
    
}
