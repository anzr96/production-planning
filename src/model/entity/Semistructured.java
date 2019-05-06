package model.entity;

import controller.StaticController;
import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by amir on 11/10/16.
 */
@Entity
@Table
public class Semistructured implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    private double total;
    @Column(columnDefinition = StaticController.stringType)
    private String semiName;
    private String semiCode;
    @JoinColumn(name = "fk_unit", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Units unit;
    @Column(columnDefinition = StaticController.stringType)
    private String semiGroups;
    private double ageMin;
    private double ageMax;
    private double needMin;
    private double needMax;
    private String description;
    @JoinColumn(name = "fk_semi_udf", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_SEMI_UDF")
    private List<UDF> udfs;
    @JoinColumn(name = "fk_semi_in", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_SEMI_IN")
    private List<SemistructuredInTimes> semistructuredInTimes;
    @JoinColumn(name = "fk_semi_out", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_SEMI_OUT")
    private List<SemistructuredOutTimes> semistructuredOutTimes;
    @JoinColumn(name = "fk_semi_rework", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_SEMI_REWORK")
    private List<SemistructuredReworkTime> semistructuredReworkTimes;
    @JoinColumn(name = "fk_semi_waste", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_SEMI_WASTE")
    private List<SemistructuredWasteTime> semistructuredWasteTimes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getSemiName() {
        return semiName;
    }

    public void setSemiName(String semiName) {
        this.semiName = semiName;
    }

    public String getSemiCode() {
        return semiCode;
    }

    public void setSemiCode(String semiCode) {
        this.semiCode = semiCode;
    }

    public Units getUnit() {
        return unit;
    }

    public void setUnit(Units unit) {
        this.unit = unit;
    }

    public String getSemiGroups() {
        return semiGroups;
    }

    public void setSemiGroups(String semiGroups) {
        this.semiGroups = semiGroups;
    }

    public double getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(double ageMin) {
        this.ageMin = ageMin;
    }

    public double getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(double ageMax) {
        this.ageMax = ageMax;
    }

    public double getNeedMin() {
        return needMin;
    }

    public void setNeedMin(double needMin) {
        this.needMin = needMin;
    }

    public double getNeedMax() {
        return needMax;
    }

    public void setNeedMax(double needMax) {
        this.needMax = needMax;
    }

    public List<UDF> getUdfs() {
        return udfs;
    }

    public void setUdfs(List<UDF> udfs) {
        this.udfs = udfs;
    }

    public List<SemistructuredInTimes> getSemistructuredInTimes() {
        return semistructuredInTimes;
    }

    public void setSemistructuredInTimes(List<SemistructuredInTimes> semistructuredInTimes) {
        this.semistructuredInTimes = semistructuredInTimes;
    }

    public List<SemistructuredOutTimes> getSemistructuredOutTimes() {
        return semistructuredOutTimes;
    }

    public void setSemistructuredOutTimes(List<SemistructuredOutTimes> semistructuredOutTimes) {
        this.semistructuredOutTimes = semistructuredOutTimes;
    }

    public List<SemistructuredReworkTime> getSemistructuredReworkTimes() {
        return semistructuredReworkTimes;
    }

    public void setSemistructuredReworkTimes(List<SemistructuredReworkTime> semistructuredReworkTimes) {
        this.semistructuredReworkTimes = semistructuredReworkTimes;
    }

    public List<SemistructuredWasteTime> getSemistructuredWasteTimes() {
        return semistructuredWasteTimes;
    }

    public void setSemistructuredWasteTimes(List<SemistructuredWasteTime> semistructuredWasteTimes) {
        this.semistructuredWasteTimes = semistructuredWasteTimes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
