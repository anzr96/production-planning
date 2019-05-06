package model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by amir on 12/5/16.
 * shift's that machine works on
 */
@Entity
@Table(name = "MACHINESHIFT")
public class MachineShift  implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    private int shift;
    private double capacity;
    private double uph;
    private double totalDate;
    private double remain;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double getUph() {
        return uph;
    }

    public void setUph(double uph) {
        this.uph = uph;
    }

    public double getTotalDate() {
        return totalDate;
    }

    public void setTotalDate(double totalDate) {
        this.totalDate = totalDate;
    }

    public double getRemain() {
        return remain;
    }

    public void setRemain(double remain) {
        this.remain = remain;
    }
}
