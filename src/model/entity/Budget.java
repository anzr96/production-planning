package model.entity;

import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by amir on 1/29/17.
 */
@Entity
@Table(name = "BUDGET")
public class Budget implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    private double price;
    private double weight;
    @JoinColumn(name = "fk_product", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_PRODUCT")
    private List<BudgetProduct> budgetProducts;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public List<BudgetProduct> getBudgetProducts() {
        return budgetProducts;
    }

    public void setBudgetProducts(List<BudgetProduct> budgetProducts) {
        this.budgetProducts = budgetProducts;
    }
}
