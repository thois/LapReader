/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.helsinki.cs.thois.lapreader.parser;

import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;


/**
 * Class parses laptimes from Strings dumped from Orion Pro LCS -device
 */
public class OrionParser implements Parser {
    
    /**
     * 
     * @param   s   Laptime in form "mm:ss.ss" 
     * @return  Laptime in thousands of seconds
     * @throws ParseException 
     */
    public static int parseTime(String s) throws ParseException {
            String[] tbl = s.split("m");
            if (tbl.length < 2)
                throw new ParseException("Time (" + s + ") is unparseable!", -1);
            int minutes = Integer.parseInt(tbl[0]);
            tbl = tbl[1].split("s");
            if (tbl.length < 2)
                throw new ParseException("Time (" + s + ") is unparseable!", -1);
            int seconds = Integer.parseInt(tbl[0]);
            int milliseconds = Integer.parseInt(tbl[1]);
            int time = 60000*minutes+1000*seconds+milliseconds*10;
            return time;
    }
    /**
     * Parses array of strings to integers
     * @param str   Table of strings to be parsed
     * @return  Array of laptimes in thousands of seconds
     * @throws ParseException 
     */
    public List<Integer> parse(String[] str) throws ParseException {
        ArrayList<Integer> laps = new ArrayList<>();
        for (String s:str) {
            s = s.replaceAll("\\s+", " ");
            if (s.split(" ").length < 2)
                throw new ParseException("Table is too short", -1);
            s = s.split(" ")[1];
            System.out.println(s);
            laps.add(parseTime(s));
        }
        return laps;
    }
}
