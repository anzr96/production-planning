package model.entity;

import controller.StaticController;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by amir on 2/6/17.
 */
@Entity
@Table
public class EventAccessibility implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    private int code;
    @Column(columnDefinition = StaticController.stringType)
    private String name;
    @Column(name = "accesses")
    private String access;
    private String kind;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
