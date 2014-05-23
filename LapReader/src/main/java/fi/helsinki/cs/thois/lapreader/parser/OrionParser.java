/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.helsinki.cs.thois.lapreader.parser;

import fi.helsinki.cs.thois.lapreader.model.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author niko
 */
public class OrionParser {
    
    public static int parseTime(String s) {
            String[] tbl = s.split("m");
            int minutes = Integer.parseInt(tbl[0]);
            tbl = tbl[1].split("s");
            int seconds = Integer.parseInt(tbl[0]);
            int milliseconds = Integer.parseInt(tbl[1]);
            int time = 6000*minutes+100*seconds+milliseconds;
            return time;
    }
    
    public static List<Integer> parse(String str) {
        ArrayList<Integer> laps = new ArrayList<>();
        String[] table = str.split(System.getProperty("line.separator"));
        for (String s:table) {
            s = s.split(" ")[1];
            laps.add(parseTime(s));
        }
        return laps;
    }
}
