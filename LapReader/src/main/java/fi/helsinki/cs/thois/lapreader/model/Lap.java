/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.thois.lapreader.model;

import com.j256.ormlite.field.DatabaseField;
import javax.persistence.*;
/**
 *
 * @author niko
 */
@Entity
public class Lap extends Model {
    
    //Time in 0,1 milliseconds
    @Column
    int time;
    
    @ManyToOne
    @DatabaseField(foreign=true)
    Heat heat;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Lap() {
        
    }
    
    public Lap(int time, Heat heat) {
        this.time = time;
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
        return seconds() + "." + thousands();
    }
    
}
