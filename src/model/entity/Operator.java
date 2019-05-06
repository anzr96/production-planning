package model.entity;

import controller.StaticController;

import javax.persistence.*;

@Entity
@Table
public class Operator {
    @Id
    @GeneratedValue
    private long id;
    @Column(columnDefinition = StaticController.stringType)
    private String code;
    @Column(columnDefinition = StaticController.stringType)
    private String name;
    @Column(columnDefinition = StaticController.stringType)
    private String job;
    @Column(columnDefinition = StaticController.stringType)
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
