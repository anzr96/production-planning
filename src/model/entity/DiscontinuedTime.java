package model.entity;

import controller.StaticController;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by amir on 1/22/17.
 */
@Entity
@Table(name = "DISCONTINUEDTIME")
public class DiscontinuedTime implements Serializable{
    @Id
    @GeneratedValue
    private long id;
    @Basic
    @Column(name = "dates")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(columnDefinition = StaticController.stringType)
    private String place;
    @Column(columnDefinition = StaticController.stringType)
    private String explain;
    private double timeOfDiscontinued;
    private double timeOfRepair;

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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public double getTimeOfDiscontinued() {
        return timeOfDiscontinued;
    }

    public void setTimeOfDiscontinued(double timeOfDiscontinued) {
        this.timeOfDiscontinued = timeOfDiscontinued;
    }

    public double getTimeOfRepair() {
        return timeOfRepair;
    }

    public void setTimeOfRepair(double timeOfRepair) {
        this.timeOfRepair = timeOfRepair;
    }
}
