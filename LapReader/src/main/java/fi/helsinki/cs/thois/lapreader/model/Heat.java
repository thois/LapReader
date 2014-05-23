/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.thois.lapreader.model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import fi.helsinki.cs.thois.lapreader.parser.*;

/**
 *
 * @author niko
 */
@Entity
public class Heat {
    
    @Column(columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    
    @ManyToOne
    private TestDay testDay;
    
    @OneToMany(cascade=CascadeType.ALL)
    private List<Lap> laps = new ArrayList<>();
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Heat() {
        time = new Date();
    }
    
    public Heat(Date time) {
        if (time==null) {
            time = new Date();
        }
        this.time = time;
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

    public List<Lap> getLaps() {
        return laps;
    }

    public void setLaps(List<Lap> laps) {
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
        lap.setHeat(this);
    }
    
    public void addLaps(String laps) {
        List<Integer> newLaps = OrionParser.parse(laps);
        for (int l:newLaps)
            addLap(new Lap(l));
    }
}
