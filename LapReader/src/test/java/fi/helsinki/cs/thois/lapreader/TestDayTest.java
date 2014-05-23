/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.thois.lapreader;

import fi.helsinki.cs.thois.lapreader.model.TestDay;
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
        testDay = new TestDay(new Date());
        testDay = new TestDay("12.12.2012");
    }
    
    @Test
    public void testToString() throws ParseException {
        String date = "12.12.2012";
        TestDay testDay = new TestDay(date);
        assertEquals(testDay.toString(), date);
    }
}
