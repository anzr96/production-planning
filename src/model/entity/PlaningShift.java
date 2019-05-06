package model.entity;

import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by amir on 12/22/16.
 * show which product , semi or raw need for this date
 */
@Entity
@Table(name = "PLANINGSHIFT")
public class PlaningShift implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int shift;
    @JoinColumn(name = "fk_raw", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_RAW")
    private List<PlaningRaw> planingRaws;
    @JoinColumn(name = "fk_product", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_PRODUCT")
    private List<PlaningProduct> planingProducts;
    @JoinColumn(name = "fk_section", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlaningSection> planingSections;

    public List<PlaningSection> getPlaningSections() {
        return planingSections;
    }

    public void setPlaningSections(List<PlaningSection> planingSections) {
        this.planingSections = planingSections;
    }

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

    public List<PlaningRaw> getPlaningRaws() {
        return planingRaws;
    }

    public void setPlaningRaws(List<PlaningRaw> planingRaws) {
        this.planingRaws = planingRaws;
    }

    public List<PlaningProduct> getPlaningProducts() {
        return planingProducts;
    }

    public void setPlaningProducts(List<PlaningProduct> planingProducts) {
        this.planingProducts = planingProducts;
    }
}
