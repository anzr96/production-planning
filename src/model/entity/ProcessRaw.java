package model.entity;

import controller.StaticController;

import javax.persistence.*;

/**
 * Created by amir on 7/3/17.
 */
@Entity
@Table
public class ProcessRaw {
    @Id
    @GeneratedValue
    private long id;
    private String rawCode;
    @Column(columnDefinition = StaticController.stringType)
    private String rawName;
    private double total;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRawCode() {
        return rawCode;
    }

    public void setRawCode(String rawCode) {
        this.rawCode = rawCode;
    }

    public String getRawName() {
        return rawName;
    }

    public void setRawName(String rawName) {
        this.rawName = rawName;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
