package fi.helsinki.cs.thois.lapreader.model;

import com.j256.ormlite.field.DatabaseField;
import java.io.Serializable;
import java.text.DecimalFormat;
import javax.persistence.*;
/**
 * Database model for one lap.
 */
@Entity
public class Lap extends Model implements Serializable {
    /**
     * Laptime in milliseconds
     */
    @Column
    private int time;
    
    /**
     * Order number for ordering laps in a heat. Starts from 1
     */
    @Column
    private int lapNumber;
    
    /**
     * Parent
     */
    @ManyToOne
    @DatabaseField(foreign=true)
    private Heat heat;
    
    /**
     * Database id for persisting the heat in database
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    /**
     * Basic constructor for persistence module
     */
    public Lap() {  
    }
    
    /**
     * Constuctor for normal use
     * @param time presents laptime
     * @param lapNumber presents order number of the lap in a heat
     * @param heat links laptime to Heat
     */
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
    
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.000");
        return df.format((double)time/1000);
    }
    
    /**
     * Calculates difference in laptime to another lap
     * @param another
     * @return time difference as a Lap object
     */
    public Lap diff(Lap another) {
        return new Lap(time-another.getTime(), lapNumber, null);
    }
    
    @Override
    public Object[] getRowData() {
        Object[] data = {lapNumber, toString()};
        return data;
    }
    
}
