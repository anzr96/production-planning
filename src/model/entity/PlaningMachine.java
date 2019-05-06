package model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class PlaningMachine {
    @Id
    @GeneratedValue
    private long id;
    private String code;
    private String name;
    private double capacity;
    private double total;
    @JoinColumn(name = "fk_semiMachine", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlaningSemiMachine> planingSemiMachines;

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

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<PlaningSemiMachine> getPlaningSemiMachines() {
        return planingSemiMachines;
    }

    public void setPlaningSemiMachines(List<PlaningSemiMachine> planingSemiMachines) {
        this.planingSemiMachines = planingSemiMachines;
    }
}
