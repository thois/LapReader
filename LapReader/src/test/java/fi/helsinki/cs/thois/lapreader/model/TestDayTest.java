package fi.helsinki.cs.thois.lapreader.model;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestDayTest {
    DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
    
    @Test
    public void testTestDayCreation() throws ParseException {
        TestDay testDay = new TestDay();
        testDay = new TestDay((Date)null);
        Date day = df.parse("24.12.2013");
        testDay = new TestDay(day);
        assertEquals(day, testDay.getDay());
        
    }
    
    @Test
    public void testTestDayCreationWithString() throws ParseException {
        Date today = new Date();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");   
        TestDay testDay = new TestDay(today);
        assert(testDay.getDay().getDate() == today.getDate());
        assert(testDay.getDay().getYear() == today.getYear());
        assert(testDay.getDay().getMonth() == today.getMonth());
    }
    
    @Test
    public void testToString() throws ParseException {
        String date = "12.12.2012";
        TestDay testDay = new TestDay(df.parse(date));
        assertEquals(testDay.toString(), date);
    }
    
    @Test
    public void testGetRowData() throws ParseException {
        String date = "12.12.2012";
        TestDay testDay = new TestDay(df.parse(date));
        Object[] row = {testDay.toString(), 0};
        Assert.assertArrayEquals(row, testDay.getRowData());
    }
    
}
