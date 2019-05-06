package model.entity;

import controller.StaticController;
import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by amir on 10/28/2016.
 * thos is product class identity
 * show which raw and semi need and how much of them
 */
@Entity
@Table
public class Product{
    @Id
    @GeneratedValue
    private int id;
    private double total;
    private double weight;
    @Column(name = "size1", columnDefinition = StaticController.stringType)
    private String size;
    @Column(columnDefinition = StaticController.stringType)
    private String design;
    @Column(columnDefinition = StaticController.stringType)
    private String pr;
    private String productCode;
    private String description;
    @JoinColumn(name = "fk_degree", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_DEGREE")
    private List<ProductDegree> productDegrees;
    @JoinColumn(name = "fk_product_in", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_PRODUCT_IN")
    private List<ProductInTime> productInTimes;
    @JoinColumn(name = "fk_product_out", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_PRODUCT_OUT")
    private List<ProductOutTime> productOutTimes;
    @JoinColumn(name = "fk_product_waste", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_PRODUCT_WASTE")
    private List<ProductWasteTime> productWasteTimes;
    @JoinColumn(name = "fk_product_udf", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_PRODUCT_UDF")
    private List<UDF> udfs;

    public List<ProductDegree> getProductDegrees() {
        return productDegrees;
    }

    public void setProductDegrees(List<ProductDegree> productDegrees) {
        this.productDegrees = productDegrees;
    }

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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public List<UDF> getUdfs() {
        return udfs;
    }

    public void setUdfs(List<UDF> udfs) {
        this.udfs = udfs;
    }

    public List<ProductInTime> getProductInTimes() {
        return productInTimes;
    }

    public void setProductInTimes(List<ProductInTime> productInTimes) {
        this.productInTimes = productInTimes;
    }

    public List<ProductOutTime> getProductOutTimes() {
        return productOutTimes;
    }

    public void setProductOutTimes(List<ProductOutTime> productOutTimes) {
        this.productOutTimes = productOutTimes;
    }

    public List<ProductWasteTime> getProductWasteTimes() {
        return productWasteTimes;
    }

    public void setProductWasteTimes(List<ProductWasteTime> productWasteTimes) {
        this.productWasteTimes = productWasteTimes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
