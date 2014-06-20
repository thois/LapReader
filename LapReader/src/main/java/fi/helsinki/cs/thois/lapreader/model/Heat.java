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
     * Parent
     */
    @ManyToOne
    @DatabaseField(foreign=true)
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
    @DatabaseField(foreign=true, foreignAutoRefresh=true)
    private Result result;
    
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
     * Constructor to
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void addLap(Lap lap) {
        laps.add(lap);
    }
    
    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(time) + ": " + result;
    }
    
    @Override
    public Object[] getRowData() {
        DateFormat df = new SimpleDateFormat("HH:mm");
        Object[] data = {df.format(time), result.toString()};
        return data;
    }
    
}