/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.helsinki.cs.thois.lapreader;

import fi.helsinki.cs.thois.lapreader.parser.OrionParser;
import fi.helsinki.cs.thois.lapreader.parser.Parser;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author niko
 */
public class OrionParserTest {
    Parser parser = new OrionParser();
    
    @Test
    public void testParseTime() throws ParseException {
        String str = "10m12s13";
        int parsedTime = OrionParser.parseTime(str);
        assert(parsedTime == 612130);
    }
    
    @Test
    public void testParse() throws ParseException {
        String[] str =
        {"01Lap 00m15s25     00m15s25","02Lap  00m15s07 00m30s32"};
        ArrayList<Integer> laps = new ArrayList<>();
        laps.add(15250);
        laps.add(15070);
        Parser parser = new OrionParser();
        List<Integer> parsedLaps = parser.parse(str);
        assert(laps.equals(parsedLaps));
    }
    
    @Test(expected = ParseException.class)
    public void testIllegalInput() throws ParseException {
        String[] str = {"01Lap00m15s2500m15s25","02Lap00m15s07 00m30s32"};
        parser.parse(str);
    }
    
    @Test(expected = ParseException.class)
    public void testIllegalTimeFormat() throws ParseException {
        String[] str = {"01Lap 00m1525 00m15s25","02 Lap00m15s00 00m30s32"};
        parser.parse(str);
    }
    
    @Test(expected = ParseException.class)
    public void testIllegalTimeFormat2() throws ParseException {
        String[] str = {"01Lap 0015s25 00m15s25","02 Lap00m15s00 00m30s32"};
        parser.parse(str);
    }
    
  @Test(expected = NumberFormatException.class)
    public void testIllegalTimeFormat3() throws ParseException {
        String[] str = {"01Lap 00ms00 00m15s25","02 Lap00m15s00 00m30s32"};
        parser.parse(str);
    }
}
