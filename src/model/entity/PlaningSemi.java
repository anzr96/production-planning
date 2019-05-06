package model.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by amir on 12/22/16.
 * show how much semi need for this date
 */
@Entity
@Table(name = "PLANINGSEMI")
public class PlaningSemi implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double total;
    private String code;
    private String name;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
