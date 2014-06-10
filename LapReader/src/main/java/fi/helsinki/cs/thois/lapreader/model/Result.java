package fi.helsinki.cs.thois.lapreader.model;

import com.j256.ormlite.field.DatabaseField;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * Database model for easier handling of result of a heat.
 */

@Entity
public class Result extends Model implements Serializable {
    /**
     * Runtime in thousands of seconds
     */
    @Column
    int time;
    /**
     * Full laps run in a heat
     */
    @Column
    int laps;
    
    /**
     * Parent
     */
    @ManyToOne
    @DatabaseField(foreign=true)
    Heat heat;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    public Result() {
        
    }
    
    public Result (List<Integer> lapTimes, Heat h) {
        int maxTime = 5*60000;
        for (int laptime:lapTimes) {
            time += laptime;
            laps++;
            if (time >= maxTime)
                break;
        }
    }
    
    public Result(int laps, int time) {
        this.laps = laps;
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public int getLaps() {
        return laps;
    }

    public Heat getHeat() {
        return heat;
    }

    public int getId() {
        return id;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setLaps(int laps) {
        this.laps = laps;
    }

    public void setHeat(Heat heat) {
        this.heat = heat;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Getting full minutes for easier formatting
     * @return full minutes
     */
    public int minutes() {
        return time/60000;
    }
    
    /**
     * Getting full seconds-full minutes for easier formatting.
     * @return full seconds
     */
    public int seconds() {
        return (time%60000)/1000;
    }
    /**
     * Getting microseconds-full seconds for easier formatting
     * @return microseconds
     */
    public int milliSeconds() {
        return time%1000;
    }
    
    @Override
    public String toString() {
        return laps + " laps " + minutes() + ":" + seconds() + "." + milliSeconds();
    }
    
}
