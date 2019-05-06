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
@Table(name = "DISCONTINUEDGROUP")
public class DiscontinuedGroup implements Serializable{
    @Id
    @GeneratedValue
    private long id;
    @Column(columnDefinition = StaticController.stringType)
    private String name;
    @JoinColumn(name = "fk_discontinued", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_DISCONTINUED")
    private List<Discontinued> discontinueds;

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

    public List<Discontinued> getDiscontinueds() {
        return discontinueds;
    }

    public void setDiscontinueds(List<Discontinued> discontinueds) {
        this.discontinueds = discontinueds;
    }
}
