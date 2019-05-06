package model.entity;

import controller.StaticController;
import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by amir on 10/7/2016.
 */
@Entity
@Table
public class RawMaterial implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    private int orderpoint;
    private int aso;
    private double totalAso;
    private int inneroutter;
    private String country;
    private double total;
    private double totallasttime;
    private String description;
    @Column(columnDefinition = StaticController.stringType)
    private String rawName;
    @Column(name = "dates")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date; //The time of last ordering
    private String rawCode;
    @Column(columnDefinition = StaticController.stringType)
    private String companyName;
    @JoinColumn(name = "fk_raw_udf", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_RAW_UDF")
    private List<UDF> udfs;
    @JoinColumn(name = "fk_raw_in", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_RAW_IN")
    private List<RawMaterialInTimes> rawMaterialInTimes;
    @JoinColumn(name = "fk_raw_out", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_RAW_OUT")
    private List<RawMaterialOutTimes> rawMaterialOutTimes;

    public double getTotalAso() {
        return totalAso;
    }

    public void setTotalAso(double totalAso) {
        this.totalAso = totalAso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderpoint() {
        return orderpoint;
    }

    public void setOrderpoint(int orderpoint) {
        this.orderpoint = orderpoint;
    }

    public int getAso() {
        return aso;
    }

    public void setAso(int aso) {
        this.aso = aso;
    }

    public int getInneroutter() {
        return inneroutter;
    }

    public void setInneroutter(int inneroutter) {
        this.inneroutter = inneroutter;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotallasttime() {
        return totallasttime;
    }

    public void setTotallasttime(double totallasttime) {
        this.totallasttime = totallasttime;
    }

    public String getRawName() {
        return rawName;
    }

    public void setRawName(String rawName) {
        this.rawName = rawName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRawCode() {
        return rawCode;
    }

    public void setRawCode(String rawCode) {
        this.rawCode = rawCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<UDF> getUdfs() {
        return udfs;
    }

    public void setUdfs(List<UDF> udfs) {
        this.udfs = udfs;
    }

    public List<RawMaterialInTimes> getRawMaterialInTimes() {
        return rawMaterialInTimes;
    }

    public void setRawMaterialInTimes(List<RawMaterialInTimes> rawMaterialInTimes) {
        this.rawMaterialInTimes = rawMaterialInTimes;
    }

    public List<RawMaterialOutTimes> getRawMaterialOutTimes() {
        return rawMaterialOutTimes;
    }

    public void setRawMaterialOutTimes(List<RawMaterialOutTimes> rawMaterialOutTimes) {
        this.rawMaterialOutTimes = rawMaterialOutTimes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
