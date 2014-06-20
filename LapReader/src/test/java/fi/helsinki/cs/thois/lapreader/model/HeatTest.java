/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.thois.lapreader.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author niko
 */
public class HeatTest {
    
    @Test
    public void testCreation() {
        Heat h = new Heat();
        h = new Heat(new Date(), new TestDay());
    }
    
    private Heat createHeat() throws ParseException {
        DateFormat df = new SimpleDateFormat("HH:mm");
        Heat h = new Heat();
        h.setTime(df.parse("15:21"));
        Result r = new Result();
        r.setLaps(15);
        r.setTime(300526);
        h.setResult(r);
        return h;
    }
    
    @Test
    public void testToString() throws ParseException {
        Heat h = createHeat();
        Assert.assertEquals("15:21: " + h.getResult(), h.toString());
    }
    
    @Test
    public void testGetRowData() throws ParseException {
        Heat h = createHeat();
        Object[] row = {"15:21", h.getResult().toString()};
        Assert.assertArrayEquals(row, h.getRowData());
    }
    
}
