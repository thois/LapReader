/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.thois.lapreader.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import fi.helsinki.cs.thois.lapreader.model.TestDay;
import java.sql.SQLException;

/**
 *
 * Custom Dao for TestDay for later implementation
 */
public class TestDayDao<ID> extends BaseDaoImpl<TestDay, ID> {
    
    protected TestDayDao(Class<TestDay> dataClass) throws SQLException {
        super(dataClass);
    }
    
    @Override
    public int update(TestDay data) throws SQLException {
        //TODO update heats
        return super.update(data);
    }
    
}
