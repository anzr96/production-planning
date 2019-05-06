package model.entity;

import controller.StaticController;
import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by amir on 11/25/16.
 * the class of machine identity and it's dependencies like the semistructured that it produce and
 * times that it is working
 */
@Entity
@Table
public class Machine implements Serializable{
    @Id
    @GeneratedValue
    private long id;
    @Column(columnDefinition = StaticController.stringType)
    private String name;
    private String code;
    private double capacity;
    private double uph;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    private String country;
    private String description;
    @JoinColumn(name = "fk_time", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_TIME")
    private List<MachineTime> machineTimes;
    @JoinColumn(name = "fk_machine_udf", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_MACHINE_UDF")
    private List<UDF> udfs;
    @JoinColumn(name = "fk_pm", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_PM")
    private List<PMTime> pmTimes;
    @JoinColumn(name = "fk_mp", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_MP")
    private List<MachinesProdcut> machinesProdcuts;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double getUph() {
        return uph;
    }

    public void setUph(double uph) {
        this.uph = uph;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<MachineTime> getMachineTimes() {
        return machineTimes;
    }

    public void setMachineTimes(List<MachineTime> machineTimes) {
        this.machineTimes = machineTimes;
    }

    public List<UDF> getUdfs() {
        return udfs;
    }

    public void setUdfs(List<UDF> udfs) {
        this.udfs = udfs;
    }

    public List<PMTime> getPmTimes() {
        return pmTimes;
    }

    public void setPmTimes(List<PMTime> pmTimes) {
        this.pmTimes = pmTimes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MachinesProdcut> getMachinesProdcuts() {
        return machinesProdcuts;
    }

    public void setMachinesProdcuts(List<MachinesProdcut> machinesProdcuts) {
        this.machinesProdcuts = machinesProdcuts;
    }
}
