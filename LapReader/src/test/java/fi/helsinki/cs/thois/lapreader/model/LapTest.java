package fi.helsinki.cs.thois.lapreader.model;

import java.text.DecimalFormat;
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
        DecimalFormat df = new DecimalFormat("#.000");
        Lap l = new Lap();
        l.setTime(time);
        Assert.assertEquals(df.format(((double)time)/1000), l.toString());
    }
    
    @Test
    public void testGetRowData() {
        Lap l = new Lap();
        l.setTime(12345);
        l.setLapNumber(25);
        Object[] row = {25, l.toString()};
        Assert.assertArrayEquals(row, l.getRowData());
    }
    
    @Test
    public void testDiff() {
        Heat heat1 = new Heat();
        Heat heat2 = new Heat();
        Lap lap1 = new Lap(12345, 1, heat1);
        Lap lap2 = new Lap(45678, 2, heat1);
        Lap diff = lap1.diff(lap2);
        assert(diff.getLapNumber() == 1);
        assert(diff.getTime() == -33333);
        Assert.assertEquals(null, diff.getHeat());
    }
    
}
