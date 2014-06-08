package fi.helsinki.cs.thois.lapreader.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * Database model for one Heat (one constant run)
 */
@Entity
public class Heat extends Model {
    
    /**
     *  Starting time
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
    
    @ForeignCollectionField(eager = false, orderColumnName="lapNumber")
    private ForeignCollection<Lap> laps;
    
    private Result result;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Heat() {
        time = new Date();
    }  
    
    public Heat(Date time, TestDay testDay) {
        if (time==null) {
            time = new Date();
        }
        this.time = time;
        this.testDay = testDay;
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
        DateFormat df = new SimpleDateFormat("HH.mm");
        return df.format(time) + ": " + result;
    }
    
}
