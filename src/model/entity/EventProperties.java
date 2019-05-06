package model.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by amir on 2/4/17.
 */
@Entity
@Table
public class EventProperties implements Serializable{
    @Id
    @GeneratedValue
    private long id;
    private int accessCode;
    private String username;
    private boolean seen;
    @Column(name = "deletes")
    private boolean delete;
    private boolean favorite;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(int accessCode) {
        this.accessCode = accessCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
