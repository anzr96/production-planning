package model.entity;

import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by amir on 11/8/16.
 * the planing class store time and shifts and their dependency
 */
@Entity
@Table(name = "PLANINGTIMES")
public class PlaningTimes implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    @Temporal(TemporalType.DATE)
    private Date time;
    private boolean edited;
    @JoinColumn(name = "fk_shift", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_SHIFT")
    private List<PlaningShift> planingShifts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public List<PlaningShift> getPlaningShifts() {
        return planingShifts;
    }

    public void setPlaningShifts(List<PlaningShift> planingShifts) {
        this.planingShifts = planingShifts;
    }
}
