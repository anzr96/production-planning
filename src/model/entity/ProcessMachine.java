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
public class ProcessMachine {
    @Id
    @GeneratedValue
    private long id;
    private String sectionCode;
    private String groupCode;
    @Column(columnDefinition = StaticController.stringType)
    private String sectionName;
    @Column(columnDefinition = StaticController.stringType)
    private String groupName;
    @JoinColumn(name = "fk_semi", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_SEMI")
    private List<ProcessSemi> processSemis;

    public ProcessMachine() {
        processSemis = new ArrayList<>();
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public List<ProcessSemi> getProcessSemis() {
        return processSemis;
    }

    public void setProcessSemis(List<ProcessSemi> processSemis) {
        this.processSemis = processSemis;
    }
}
