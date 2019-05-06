package model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by amir on 1/27/17.
 */
@Entity
@Table
public class RawMaterialOutTimes implements Serializable{
    @Id
    @GeneratedValue
    private long id;
    private double total;
    @Column(name = "dates")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private String batchNumber;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }
}
