/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.thois.lapreader.model;

import com.j256.ormlite.field.DatabaseField;
import java.text.DecimalFormat;
import javax.persistence.*;
/**
 *
 * @author niko
 */
@Entity
public class Lap extends Model {
    
    //Time in 0,1 milliseconds
    @Column
    private int time;
    
    @Column
    private int lapNumber;
    
    @ManyToOne
    @DatabaseField(foreign=true)
    private Heat heat;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    public Lap() {
        
    }
    
    public Lap(int time, int lapNumber, Heat heat) {
        this.time = time;
        this.lapNumber = lapNumber;
        this.heat = heat;
    }
    
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Heat getHeat() {
        return heat;
    }

    public void setHeat(Heat heat) {
        this.heat = heat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int seconds() {
        return time/1000;
    }
    
    public int thousands() {
        return time%1000;
    }
    
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.000");
        return df.format((double)time/1000);
    }
    
}
