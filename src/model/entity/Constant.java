package model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by amir on 2/11/17.
 */
@Entity
@Table
public class Constant implements Serializable{
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "dates")
    @Temporal(TemporalType.DATE)
    private Date date;
    private int shiftHour;
    private int workingDatesNumber;
    @Temporal(TemporalType.DATE)
    private Date beginOfYear;
    @Temporal(TemporalType.DATE)
    private Date endOfYear;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getShiftHour() {
        return shiftHour;
    }

    public void setShiftHour(int shiftHour) {
        this.shiftHour = shiftHour;
    }

    public int getWorkingDatesNumber() {
        return workingDatesNumber;
    }

    public void setWorkingDatesNumber(int workingDatesNumber) {
        this.workingDatesNumber = workingDatesNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getBeginOfYear() {
        return beginOfYear;
    }

    public void setBeginOfYear(Date beginOfYear) {
        this.beginOfYear = beginOfYear;
    }

    public Date getEndOfYear() {
        return endOfYear;
    }

    public void setEndOfYear(Date endOfYear) {
        this.endOfYear = endOfYear;
    }
}
