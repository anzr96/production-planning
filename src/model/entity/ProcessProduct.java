package model.entity;

import controller.StaticController;
import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amir on 7/3/17.
 */
@Entity
@Table
public class ProcessProduct {
    @Id
    @GeneratedValue
    private long id;
    private String code;
    @Column(columnDefinition = StaticController.stringType)
    private String name;
    @JoinColumn(name = "fk_process", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_PROCESS")
    private List<Process> processes;

    public ProcessProduct() {
        processes = new ArrayList<>();
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

    public List<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }
}
