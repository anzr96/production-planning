package model.entity;

import controller.StaticController;
import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amir on 7/5/17.
 */
@Entity
@Table(name = "MACHINEGROUP")
public class MachineGroup implements Serializable{
    @Id
    @GeneratedValue
    private long id;
    private String code;
    @Column(columnDefinition = StaticController.stringType)
    private String name;
    @JoinColumn(name = "fk_machine", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_MACHINE")
    private List<Machine> machines;
    @JoinColumn(name = "fk_machine_semi", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_MACHINE_SEMI")
    private List<Semistructured> machineSemis;
    @JoinColumn(name = "fk_machine_product", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_MACHINE_PORDUCT")
    private List<Product> machineProducts;

    public MachineGroup() {
        machines = new ArrayList<>();
        machineSemis = new ArrayList<>();
        machineProducts = new ArrayList<>();
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

    public List<Machine> getMachines() {
        return machines;
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }

    public List<Semistructured> getMachineSemis() {
        return machineSemis;
    }

    public void setMachineSemis(List<Semistructured> machineSemis) {
        this.machineSemis = machineSemis;
    }

    public List<Product> getMachineProducts() {
        return machineProducts;
    }

    public void setMachineProducts(List<Product> machineProducts) {
        this.machineProducts = machineProducts;
    }
}
