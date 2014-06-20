package fi.helsinki.cs.thois.lapreader.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;

public class HeatTest {
    DateFormat df = new SimpleDateFormat("HH:mm");
    
    @Test
    public void testCreation() throws ParseException {
        Heat h = new Heat();
        Date date = df.parse("14:56");
        h = new Heat(date, new TestDay());
        Assert.assertEquals(date, h.getTime());
        h = new Heat(null, null);
    }
    
    private Heat createHeat() throws ParseException {
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
