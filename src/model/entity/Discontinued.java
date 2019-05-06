package model.entity;

import controller.StaticController;
import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by amir on 1/22/17.
 */
@Entity
@Table(name = "DISCONTINUED")
public class Discontinued implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Column(columnDefinition = StaticController.stringType)
    private String name;
    @JoinColumn(name = "fk_time", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_TIME")
    private List<DiscontinuedTime> discontinuedTimes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DiscontinuedTime> getDiscontinuedTimes() {
        return discontinuedTimes;
    }

    public void setDiscontinuedTimes(List<DiscontinuedTime> discontinuedTimes) {
        this.discontinuedTimes = discontinuedTimes;
    }
}
