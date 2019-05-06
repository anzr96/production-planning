package model.entity;

import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by amir on 12/5/16.
 * time that machine work
 */
@Entity
@Table(name = "MACHINETIME")
public class MachineTime implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Basic
    @Column(name = "dates")
    @Temporal(TemporalType.DATE)
    private Date date;
    @JoinColumn(name = "fk_shift", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_SHIFT")
    private List<MachineShift> machineShifts;

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

    public List<MachineShift> getMachineShifts() {
        return machineShifts;
    }

    public void setMachineShifts(List<MachineShift> machineShifts) {
        this.machineShifts = machineShifts;
    }
}
