/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.thois.lapreader.parser;

import java.text.ParseException;
import java.util.List;

/**
 *Interface for laptime parsers
 */
public interface Parser {
    
    /**
     * Parses array of strings to integers
     * @param str   Table of strings to be parsed
     * @return  Array of laptimes in thousands of seconds
     * @throws ParseException 
     */
    public List<Integer> parse(String[] str) throws ParseException ;
}
