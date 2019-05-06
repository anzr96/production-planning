package model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by amir on 1/30/17.
 */
@Entity
@Table
public class PMTime implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "dates")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date start;
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date end;

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

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
