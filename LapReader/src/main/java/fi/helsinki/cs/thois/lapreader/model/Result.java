package fi.helsinki.cs.thois.lapreader.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    public Result() {
        
    }
    
    public Result (List<Integer> lapTimes) {
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

    public int getId() {
        return id;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setLaps(int laps) {
        this.laps = laps;
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
     * Getting milliseconds-full seconds for easier formatting
     * @return microseconds
     */
    public int milliSeconds() {
        return time%1000;
    }
    
    /**
     * Calculates average laptime for a heat
     * @return average laptime as Lap object
     */
    public Lap avgLapTime() {
        return new Lap(time/laps, 0, null);
    }
    
    /**
     * Formats time to string
     * @return time formatted to string
     */
    public String timeToString() {
        return String.format("%02d", minutes()) +
                ":" + String.format("%02d", seconds()) + "." +
                String.format("%03d", milliSeconds());
    }
    
    @Override
    public String toString() {
        return laps + " laps " + timeToString();
    }
    
    @Override
    public Object[] getRowData() {
        Object[] data = {toString()};
        return data;
    }
}
