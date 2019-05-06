package model.entity;

import controller.StaticController;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by amir on 1/29/17.
 */
@Entity
@Table
public class SemistructuredOutTimes implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "dates")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private double total;
    @Column(columnDefinition = StaticController.stringType)
    private String batchNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
