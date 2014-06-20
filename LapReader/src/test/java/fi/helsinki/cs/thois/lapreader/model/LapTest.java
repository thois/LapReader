package fi.helsinki.cs.thois.lapreader.model;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author niko
 */
public class LapTest {
    
    @Test
    public void testCreation() {
        Lap l = new Lap();
        int time = 12345;
        Heat heat = new Heat();
        l = new Lap(time, 1, heat);
        Assert.assertEquals(l.getHeat(), heat);
        assert(l.getTime() == time);
        assert(l.getLapNumber() == 1);
    }
    
    @Test
    public void testToString() {
        int time = 12345;
        Lap l = new Lap();
        l.setTime(time);
        Assert.assertEquals("12.345", l.toString());
    }
    
    @Test
    public void testGetRowData() {
        Lap l = new Lap();
        l.setTime(12345);
        l.setLapNumber(25);
        Object[] row = {25, 12345};
        Assert.assertArrayEquals(row, l.getRowData());
    }
    
}
