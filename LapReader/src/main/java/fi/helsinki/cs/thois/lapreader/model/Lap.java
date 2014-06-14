package fi.helsinki.cs.thois.lapreader.model;

import com.j256.ormlite.field.DatabaseField;
import java.text.DecimalFormat;
import javax.persistence.*;
/**
 * Database model for one lap.
 */
@Entity
public class Lap extends Model {
    /**
     * Laptime in milliseconds
     */
    @Column
    private int time;
    
    /**
     * Number for ordering laps in a heat. Starts from 1
     */
    @Column
    private int lapNumber;
    
    /**
     * Parent
     */
    @ManyToOne
    @DatabaseField(foreign=true)
    private Heat heat;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    public Lap() {
        
    }
    
    public Lap(int time, int lapNumber) {
        this.time = time;
        this.lapNumber = lapNumber;
    }
    
    public Lap(int time, int lapNumber, Heat heat) {
        this.time = time;
        this.lapNumber = lapNumber;
        this.heat = heat;
    }
    
    public int getTime() {
        return time;
    }

    public void setLapNumber(int lapNumber) {
        this.lapNumber = lapNumber;
    }

    public int getLapNumber() {
        return lapNumber;
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
    
    /**
     * Get full seconds from laptime for easier formatting
     * @return full seconds
     */
    public int seconds() {
        return time/1000;
    }
    
    /**
     * Get thousands of seconds not including full seconds
     * @return thousands of seconds
     */
    public int thousands() {
        return time%1000;
    }
    
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.000");
        return df.format((double)time/1000);
    }
    
    public Lap diff(Lap another) {
        return new Lap(time-another.getTime(), lapNumber);
    }
    
    @Override
    public Object[] getRowData() {
        Object[] data = {lapNumber, time};
        return data;
    }
    
}
