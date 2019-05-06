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
public class Process {
    @Id
    @GeneratedValue
    private long id;
    private String sectionCode;
    private String groupCode;
    @Column(columnDefinition = StaticController.stringType)
    private String sectionName;
    @Column(columnDefinition = StaticController.stringType)
    private String groupName;
    @Column(columnDefinition = StaticController.stringType)
    private String outputName;
    private String outputCode;
    private double totalOutput;
    @JoinColumn(name = "fk_inputMachine", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_INPUTMACHINE")
    private List<ProcessMachine> inputMachines;
    @JoinColumn(name = "fk_outputMachine", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_OUTPUTMACHINE")
    private List<ProcessMachine> outputMachines;
    @JoinColumn(name = "fk_rawInputsRaw", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_RAWINPUTSRAW")
    private List<ProcessRaw> rawInputsRaw;

    public Process() {
        inputMachines = new ArrayList<>();
        outputMachines = new ArrayList<>();
        rawInputsRaw = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getOutputName() {
        return outputName;
    }

    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }

    public String getOutputCode() {
        return outputCode;
    }

    public void setOutputCode(String outputCode) {
        this.outputCode = outputCode;
    }

    public List<ProcessMachine> getInputMachines() {
        return inputMachines;
    }

    public void setInputMachines(List<ProcessMachine> inputMachines) {
        this.inputMachines = inputMachines;
    }

    public List<ProcessMachine> getOutputMachines() {
        return outputMachines;
    }

    public void setOutputMachines(List<ProcessMachine> outputMachines) {
        this.outputMachines = outputMachines;
    }

    public List<ProcessRaw> getRawInputsRaw() {
        return rawInputsRaw;
    }

    public void setRawInputsRaw(List<ProcessRaw> rawInputsRaw) {
        this.rawInputsRaw = rawInputsRaw;
    }

    public double getTotalOutput() {
        return totalOutput;
    }

    public void setTotalOutput(double totalOutput) {
        this.totalOutput = totalOutput;
    }
}
