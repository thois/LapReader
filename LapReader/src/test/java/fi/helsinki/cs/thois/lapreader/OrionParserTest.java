/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.helsinki.cs.thois.lapreader;

import fi.helsinki.cs.thois.lapreader.parser.OrionParser;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author niko
 */
public class OrionParserTest {
    @Test
    public void testParseTime() {
        int number = 71234;
        Time time = new Time(71234);
        String str = time.getMinutes() + "m" + time.getSeconds() + "s" + number%100;
        int parsedTime = OrionParser.parseTime(str);
        assert(time.getTime() == parsedTime);
    }
    
    public void testParse() {
        String str = "01Lap 00m15s25  00m15s25\n02Lap 00m15s07 00m30s32";
        ArrayList<Integer> laps = new ArrayList<>();
        laps.add(1525);
        laps.add(1507);
        List<Integer> parsedLaps = OrionParser.parse(str);
        assert(laps.equals(parsedLaps));
    }
}
