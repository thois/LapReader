/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.thois.lapreader.model;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author niko
 */
public class TestDayTest {
    @Test
    public void testTestDayCreation() throws ParseException {
        TestDay testDay = new TestDay();
        testDay = new TestDay("");
        Date today = new Date();
        testDay = new TestDay(today);
        assertEquals(today, testDay.getDay());
        
    }
    
    @Test
    public void testTestDayCreationWithString() throws ParseException {
        Date today = new Date();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");   
        TestDay testDay = new TestDay(df.format(today));
        assert(testDay.getDay().getDate() == today.getDate());
        assert(testDay.getDay().getYear() == today.getYear());
        assert(testDay.getDay().getMonth() == today.getMonth());
    }
    
    @Test
    public void testToString() throws ParseException {
        String date = "12.12.2012";
        TestDay testDay = new TestDay(date);
        assertEquals(testDay.toString(), date);
    }
    
}
