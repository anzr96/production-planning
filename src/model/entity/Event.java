package model.entity;

import controller.StaticController;
import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by amir on 1/24/17.
 */
@Entity
@Table
public class Event implements Serializable{
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "dates")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Temporal(TemporalType.TIMESTAMP)
    private Date editedDate;
    private boolean edited = false;
    @Column(name = "accesses", columnDefinition = StaticController.stringType)
    private String access;
    @Column(columnDefinition = StaticController.stringType)
    private String kind;
    @Column(columnDefinition = StaticController.stringType)
    private String title;
    @Column(columnDefinition = StaticController.stringType)
    private String description;
    @Column(name = "urlString")
    private String url;
    @JoinColumn(name = "fk_properties", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_PROPERTIES")
    private List<EventProperties> propertiesList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEditedDate() {
        return editedDate;
    }

    public void setEditedDate(Date editedDate) {
        this.editedDate = editedDate;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<EventProperties> getPropertiesList() {
        return propertiesList;
    }

    public void setPropertiesList(List<EventProperties> propertiesList) {
        this.propertiesList = propertiesList;
    }
}
