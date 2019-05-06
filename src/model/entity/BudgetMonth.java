package model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class BudgetMonth {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "dates")
    @Temporal(TemporalType.DATE)
    private Date date;
    private double total;
    private double remainTotal;
    private double weight;
    private double remainWeight;
    private double price;

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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRemainTotal() {
        return remainTotal;
    }

    public void setRemainTotal(double remainTotal) {
        this.remainTotal = remainTotal;
    }

    public double getRemainWeight() {
        return remainWeight;
    }

    public void setRemainWeight(double remainWeight) {
        this.remainWeight = remainWeight;
    }
}
