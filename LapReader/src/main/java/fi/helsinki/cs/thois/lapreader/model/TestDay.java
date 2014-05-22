
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.helsinki.cs.thois.lapreader.model;

/**
 *
 * @author nlindval
 */

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.*;


@Entity
public class TestDay {

    @Column(columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date day;
    
    private String conditions = "";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    
    public TestDay() {
        day = new Date();
    }
    
    public TestDay(Date day) {
        this.day = day;
    }
    
    
    public TestDay(String date) throws ParseException {
        if (date.isEmpty()) {
            day = new Date();
        } else {
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            day = df.parse(date);
        }
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public void setDay(Date day) {
        this.day = day;
    }
    
    public Date getDay() {
        return day;
    }
    
    public void setConditions(String conditions) {
        this.conditions = conditions;
    }
    
    public String getConditions() {
        return conditions;
    }
    
    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        return df.format(day);
    }
}
