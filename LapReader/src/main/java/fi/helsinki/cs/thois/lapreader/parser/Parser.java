/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.thois.lapreader.parser;

import java.text.ParseException;
import java.util.List;

/**
 *
 * @author niko
 */
public interface Parser {
    public List<Integer> parse(String[] str) throws ParseException ;
}
