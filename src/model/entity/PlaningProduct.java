package model.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by amir on 11/8/16.
 * show how much product need for this date
 */
@Entity
@Table(name = "PLANINGPRODUCT")
public class PlaningProduct implements Serializable{
    @Id
    @GeneratedValue
    private int id;
    private String code;
    private String name;
    private long total;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getProductCode() {
        return code;
    }

    public void setProductCode(String productCode) {
        this.code = productCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
