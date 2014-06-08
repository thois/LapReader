/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.helsinki.cs.thois.lapreader;

import fi.helsinki.cs.thois.lapreader.parser.OrionParser;
import java.sql.Time;
import java.text.ParseException;
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
    public void testParseTime() throws ParseException {
        int number = 71230;
        Time time = new Time(number);
        String str = time.getMinutes() + "m" + time.getSeconds() + "s" + (number%1000)/10;
        int parsedTime = OrionParser.parseTime(str);
        assert(time.getTime() == parsedTime);
    }
    
    public void testParse() throws ParseException {
        String[] str = {"01Lap 00m15s25     00m15s25","02Lap  00m15s07 00m30s32"};
        ArrayList<Integer> laps = new ArrayList<>();
        laps.add(15250);
        laps.add(15070);
        List<Integer> parsedLaps = OrionParser.parse(str);
        assert(laps.equals(parsedLaps));
    }
}
