package model.entity;

import controller.StaticController;
import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by amir on 11/25/16.
 * show wich degree we have for this date
 */
@Entity
@Table
public class ProductInTime implements Serializable{
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "dates")
    @Temporal(TemporalType.DATE)
    private Date date;
    private double total;
    private double remain;
    @Column(columnDefinition = StaticController.stringType)
    private String batchNumber;
    @JoinColumn(name = "fk_degree", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name = "INDEX_COL_DEGREE")
    private List<ProductDegreeTime> productDegreeTimes;

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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getRemain() {
        return remain;
    }

    public void setRemain(double remain) {
        this.remain = remain;
    }

    public List<ProductDegreeTime> getProductDegreeTimes() {
        return productDegreeTimes;
    }

    public void setProductDegreeTimes(List<ProductDegreeTime> productDegreeTimes) {
        this.productDegreeTimes = productDegreeTimes;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }
}
