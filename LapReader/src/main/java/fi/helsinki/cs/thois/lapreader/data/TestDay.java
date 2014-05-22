
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.helsinki.cs.thois.lapreader.data;

/**
 *
 * @author nlindval
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.Temporal;

@Entity
public class TestDay {
    
    
    //@Temporal(javax.persistence.TemporalType.DATE)
    private String day;
    
    private String conditions = "";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    
    public TestDay() {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        day = df.format(new Date());
    }
    
    public TestDay(Date date) {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        this.day = df.format(date);
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public void setDay(String date) {
        this.day = date;
    }
    
    public String getDay() {
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
        //DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        //return df.format(day);
        return day;
    }
}
