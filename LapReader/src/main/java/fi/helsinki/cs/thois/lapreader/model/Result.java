/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.thois.lapreader.model;

/**
 *
 * @author niko
 */
class Result extends Model {
    int time;
    int laps;
    
    Result() {
        
    }
    
    Result(int laps, int time) {
        this.laps = laps;
        this.time = time;
    }
    
    
    public int minutes() {
        return time/60000;
    }
    
    public int seconds() {
        return (time%60000)/1000;
    }
    
    public int microseconds() {
        return time%1000;
    }
    
    @Override
    public String toString() {
        return laps + " laps " + minutes() + ":" + seconds() + "." + microseconds();
    }
    
}
