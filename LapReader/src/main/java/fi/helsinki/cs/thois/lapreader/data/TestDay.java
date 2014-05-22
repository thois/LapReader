
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
import javax.persistence.Id;
import javax.persistence.Temporal;

@Entity
public class TestDay {
    
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date testDate;
    
    private String conditions;
    
    @Id
    private Integer id;
    
    public TestDay() {
        testDate = new Date();
    }
    
    public TestDay(Date date) {
        this.testDate = date;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setDate(Date date) {
        this.testDate = date;
    }
    
    public Date getDate(Date date) {
        return date;
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
        return df.format(testDate);
    }
}
