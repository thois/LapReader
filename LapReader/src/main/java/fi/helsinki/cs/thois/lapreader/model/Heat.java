package fi.helsinki.cs.thois.lapreader.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.*;

/**
 * Database model for one Heat (one constant run)
 */
@Entity
public class Heat extends Model implements Serializable {
    
    /**
     *  Starting time of the heat. Only time matters.
     */
    @Column(columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    
    /**
     * Parent testDay object
     */
    @ManyToOne
    @DatabaseField(foreign=true, columnDefinition = "integer references testday(id) on delete cascade")
    private TestDay testDay;
    
    /**
     * Laps presenting laptimes in the heat
     */
    @ForeignCollectionField(eager = false, orderColumnName="lapNumber")
    private ForeignCollection<Lap> laps;
    
    /**
     * Result completed in the heat
     */
    @OneToOne
    @DatabaseField(foreign=true, foreignAutoRefresh=true, columnDefinition = "integer references result(id) on delete cascade")
    private Result result;
    
    /**
     * Setup changes in plain String for archiving
     */
    @DatabaseField
    private String setupChanges;
    
    /**
     * Track temperature when the heat was run
     */
    @DatabaseField
    private Integer trackTemp;
    
    /**
     * Air temperature when the heat was run
     */
    @DatabaseField
    private Integer airTemp;
    
    /**
     * Database id for persisting the heat in database
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * basic constructor that uses current time as the heat starting time
     */
    public Heat() {
        time = new Date();
    }  
    
    /**
     * Constructor for normal use
     * @param time presenting starting time of the heat
     * @param testDay links the heat to day
     */
    public Heat(Date time, TestDay testDay) {
        if (time==null) {
            time = new Date();
        }
        this.time = time;
        this.testDay = testDay;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
    
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public TestDay getTestDay() {
        return testDay;
    }

    public void setTestDay(TestDay testDay) {
        this.testDay = testDay;
    }

    public ForeignCollection<Lap> getLaps() {
        return laps;
    }

    public void setLaps(ForeignCollection<Lap> laps) {
        this.laps = laps;
    }

    public String getSetupChanges() {
        return setupChanges;
    }

    public void setSetupChanges(String setupChanges) {
        this.setupChanges = setupChanges;
    }

    public Integer getTrackTemp() {
        return trackTemp;
    }

    public void setTrackTemp(Integer trackTemp) {
        this.trackTemp = trackTemp;
    }

    public Integer getAirTemp() {
        return airTemp;
    }

    public void setAirTemp(Integer airTemp) {
        this.airTemp = airTemp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Adds lap to the Heat. A heat must exists before using this and the heat
     * must already be persisted in database
     * @param lap to be added
     */
    public void addLap(Lap lap) {
        laps.add(lap);
    }
    
    /**
     * Formats time of run to string
     * @return time as a string
     */
    public String timeToString() {
        DateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(time);
    }
    
    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("HH:mm");
        return timeToString() + ": " + result;
    }
    
    @Override
    public Object[] getRowData() {
        DateFormat df = new SimpleDateFormat("HH:mm");
        Object[] data = {timeToString(), result.toString()};
        return data;
    }
    
    /**
     * Get array of total time after each lap
     * @return array of total times as Results
     */
    public Result[] totalTimes() {
        return totalTimes(laps.toArray(new Lap[0]));
    }
    
    /**
     * Get array of total time after each lap
     * @param lapsArray
     * @return array of total times as Results
     */
    public Result[] totalTimes(Lap[] lapsArray) {
        Result[] times = new Result[lapsArray.length];
        int time = 0;
        for (int i = 0; i < lapsArray.length; i++) {
            time += lapsArray[i].getTime();
            times[i] = new Result(-1, time);
        }
        return times;
    }
    
}