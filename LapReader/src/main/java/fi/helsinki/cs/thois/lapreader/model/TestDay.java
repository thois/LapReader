package fi.helsinki.cs.thois.lapreader.model;



import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.ForeignCollectionField;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.*;

/**
 * Database model for a day. Named to TestDay to avoid impact with Java's Date.
 */
@Entity
public class TestDay extends Model implements Serializable {

    /**
     * Keeps date of the day. Time don't matter
     */
    @Column(columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date day;
    
    /**
     * Track conditions in a string
     */
    private String conditions = "";
    
    /**
     * 
     */
    @ForeignCollectionField(eager = false, orderColumnName="time")
    ForeignCollection<Heat> heats;
    
    /**
     * Used solely for persisting the testday in the database
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    /**
     * Basic constructor for use in persistence module
     */
    public TestDay() {
        day = new Date();
    }

    /**
     * Constructor for creating testDay from Java's date object
     * @param day as a date object
     */
    public TestDay(Date day) {
        if (day == null)
            day = new Date();
        this.day = day;
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

    public ForeignCollection<Heat> getHeats() {
        return heats;
    }

    public void setHeats(ForeignCollection<Heat> heats) {
        this.heats = heats;
    }
    
    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        return df.format(day);
    }
    
    @Override
    public Object[] getRowData() {
        Integer heatCount = 0;
        if (heats != null)
            heatCount = heats.size();
        Object[] data = {toString(), heatCount};
        return data;
    }
}
