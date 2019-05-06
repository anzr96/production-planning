package model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class PlaningSection {
    @Id
    @GeneratedValue
    private long id;
    private String code;
    private String name;
    @JoinColumn(name = "fk_group", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlaningGroup> planingGroupLis;

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

    public List<PlaningGroup> getPlaningGroupLis() {
        return planingGroupLis;
    }

    public void setPlaningGroupLis(List<PlaningGroup> planingGroupLis) {
        this.planingGroupLis = planingGroupLis;
    }
}
