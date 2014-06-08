/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.helsinki.cs.thois.lapreader.parser;

import fi.helsinki.cs.thois.lapreader.model.*;
import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;


/**
 *
 * @author niko
 */
public class OrionParser {
    
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
    
    public static List<Integer> parse(String[] str) throws ParseException {
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
