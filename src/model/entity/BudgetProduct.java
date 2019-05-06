package model.entity;

import controller.StaticController;
import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by amir on 1/29/17.
 */
@Entity
@Table
public class BudgetProduct implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Column(columnDefinition = StaticController.stringType)
    private String code;
    @Column(columnDefinition = StaticController.stringType)
    private String name;
    @Column(columnDefinition = StaticController.stringType)
    private String design;
    @Column(columnDefinition = StaticController.stringType)
    private String pr;
    private double total;
    private double remain;
    private double price;
    @JoinColumn(name = "fk_month", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_MONTH")
    private List<BudgetMonth> budgetMonths;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getPr() {
        return pr;
    }

    public void setPr(String pr) {
        this.pr = pr;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<BudgetMonth> getBudgetMonths() {
        return budgetMonths;
    }

    public void setBudgetMonths(List<BudgetMonth> budgetMonths) {
        this.budgetMonths = budgetMonths;
    }
}
