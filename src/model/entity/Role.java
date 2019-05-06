package model.entity;

import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by amir on 1/24/17.
 */
@Entity
@Table
public class Role implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private int code;
    private String name;
    @JoinColumn(name = "fk_access", referencedColumnName = "id")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name = "INDEX_COL_ACCESS")
    private List<Accessibility> accessibilities;
    @JoinColumn(name = "fk_eventaccess", referencedColumnName = "id")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name = "INDEX_COL_EVENTACCESS")
    private List<EventAccessibility> eventAccessibilities;
    @JoinColumn(name = "fk_user", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name = "INDEX_COL_USER")
    private List<User> users;

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

    public List<Accessibility> getAccessibilities() {
        return accessibilities;
    }

    public void setAccessibilities(List<Accessibility> accessibilities) {
        this.accessibilities = accessibilities;
    }

    public List<EventAccessibility> getEventAccessibilities() {
        return eventAccessibilities;
    }

    public void setEventAccessibilities(List<EventAccessibility> eventAccessibilities) {
        this.eventAccessibilities = eventAccessibilities;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}

