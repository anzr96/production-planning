package model.entity;

import controller.StaticController;
import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amir on 7/5/17.
 */
@Entity
@Table
public class Section {
    @Id
    @GeneratedValue
    private long id;
    private String code;
    @Column(columnDefinition = StaticController.stringType)
    private String name;
    @JoinColumn(name = "fk_machine_group", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_MACHINE_GROUP")
    private List<MachineGroup> machineGroups;

    public Section() {
        machineGroups = new ArrayList<>();
    }

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

    public List<MachineGroup> getMachineGroups() {
        return machineGroups;
    }

    public void setMachineGroups(List<MachineGroup> machineGroups) {
        this.machineGroups = machineGroups;
    }
}
