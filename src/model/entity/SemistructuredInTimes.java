package model.entity;

import controller.StaticController;
import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by amir on 1/29/17.
 */
@Entity
@Table
public class SemistructuredInTimes implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "dates")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private double total;
    @Column(columnDefinition = StaticController.stringType)
    private String batchNumber;
    private int shift;
    @Column(name = "groups")
    private String group;
    @ElementCollection
    @CollectionTable(name = "semiInMachines")
    private Set<Long> machineId;
    @Column(columnDefinition = StaticController.stringType)
    private String description;
    @JoinColumn(name = "fk_semi_in_operator", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_SEMIINoperator")
    private Set<Operator> operatorSet;
    @ElementCollection
    @CollectionTable(name = "semiInMaterials")
    private Set<String> materials;
    @ElementCollection
    @CollectionTable(name = "semiInSemis")
    private Set<String> semis;
    @JoinColumn(name = "fk_semi_in_udf", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_SEMI_IN_UDF")
    private List<UDF> udfs;

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

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Set<Long> getMachineId() {
        return machineId;
    }

    public void setMachineId(Set<Long> machineId) {
        this.machineId = machineId;
    }

    public Set<Operator> getOperatorSet() {
        return operatorSet;
    }

    public void setOperatorSet(Set<Operator> operatorSet) {
        this.operatorSet = operatorSet;
    }

    public Set<String> getMaterials() {
        return materials;
    }

    public void setMaterials(Set<String> materials) {
        this.materials = materials;
    }

    public Set<String> getSemis() {
        return semis;
    }

    public void setSemis(Set<String> semis) {
        this.semis = semis;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UDF> getUdfs() {
        return udfs;
    }

    public void setUdfs(List<UDF> udfs) {
        this.udfs = udfs;
    }
}
