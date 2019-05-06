package model.entity;

import controller.StaticController;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by amir on 1/24/17.
 */
@Entity
@Table(name = "ACCESSIBILITY")
public class Accessibility {
    @Id
    @GeneratedValue
    private long id;
    private int code;
    @Column(columnDefinition = StaticController.stringType)
    private String name;
    private String url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
