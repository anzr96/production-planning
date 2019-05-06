package model.entity;

import controller.StaticController;

import javax.persistence.*;

/**
 * Created by amir on 7/3/17.
 */
@Entity
@Table
public class ProcessSemi {
    @Id
    @GeneratedValue
    private long id;
    private String semiCode;
    @Column(columnDefinition = StaticController.stringType)
    private String semiName;
    private double total;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSemiCode() {
        return semiCode;
    }

    public void setSemiCode(String semiCode) {
        this.semiCode = semiCode;
    }

    public String getSemiName() {
        return semiName;
    }

    public void setSemiName(String semiName) {
        this.semiName = semiName;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
