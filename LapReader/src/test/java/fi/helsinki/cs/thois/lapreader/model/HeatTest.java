/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.thois.lapreader.model;

import java.util.Date;
import org.junit.Test;

/**
 *
 * @author niko
 */
public class HeatTest {
    
    @Test
    public void testCreation() {
        Heat h = new Heat();
        h = new Heat(new Date(), new TestDay());
    }
    
}
