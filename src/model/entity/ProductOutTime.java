package model.entity;

import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by amir on 12/19/16.
 */
@Entity
@Table(name = "SELLTIMES")
public class ProductOutTime implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Basic
    @Column(name = "dates")
    @Temporal(TemporalType.DATE)
    private Date date;
    private double total;
    private String batchNumber;
    @JoinColumn(name = "fk_degree", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name = "INDEX_COL_DEGREE")
    private List<ProductDegreeTime> productDegreeTimes;

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public List<ProductDegreeTime> getProductDegreeTimes() {
        return productDegreeTimes;
    }

    public void setProductDegreeTimes(List<ProductDegreeTime> productDegreeTimes) {
        this.productDegreeTimes = productDegreeTimes;
    }

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
}
