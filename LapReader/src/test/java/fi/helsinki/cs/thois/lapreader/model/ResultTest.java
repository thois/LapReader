package fi.helsinki.cs.thois.lapreader.model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ResultTest {
    
    @Test
    public void testCreation() {
        List<Integer> lapTimes = new ArrayList<>(Arrays.asList(1234, 1234));
        Result result = new Result(lapTimes);
        assert(result.getLaps() == 2);
        assert(result.getTime() == (1234*2));
    }
    
    private Result createResult() {
        List<Integer> lapTimes = new ArrayList<>(Arrays.asList(15250, 15070,
                15100, 15220, 15290, 15380, 15010, 15350, 15270, 15170, 15210,
                15280, 14970, 15450, 15490, 15310, 15430, 15180, 15280, 15550,
                15550));
        return new Result(lapTimes);
    }
    
    @Test
    public void testMinutes() {
        Result r = createResult();
        assert(r.minutes() == 5);
    }
    
    @Test
    public void testSeconds() {
        Result r = createResult();
        assert(r.seconds() == 5);
    }
    
    @Test
    public void testMilliSeconds() {
        Result r = createResult();
        assert(r.milliSeconds() == 260);
    }
    
    @Test
    public void testToString() {
        Result r = createResult();
        Assert.assertEquals("20 laps 05:05.260", r.toString());
    }
    
    @Test
    public void testGetRowData() {
        Result r = createResult();
        Object[] row = {r.toString()};
        Assert.assertArrayEquals(row, r.getRowData());
    }
}
