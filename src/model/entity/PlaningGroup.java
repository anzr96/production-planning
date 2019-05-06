package model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class PlaningGroup {
    @Id
    @GeneratedValue
    private long id;
    private String code;
    private String name;
    @JoinColumn(name = "fk_machine", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlaningMachine> planingMachineList;
    @JoinColumn(name = "fk_semi", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlaningSemi> planingSemis;

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

    public List<PlaningMachine> getPlaningMachineList() {
        return planingMachineList;
    }

    public void setPlaningMachineList(List<PlaningMachine> planingMachineList) {
        this.planingMachineList = planingMachineList;
    }

    public List<PlaningSemi> getPlaningSemis() {
        return planingSemis;
    }

    public void setPlaningSemis(List<PlaningSemi> planingSemis) {
        this.planingSemis = planingSemis;
    }
}
