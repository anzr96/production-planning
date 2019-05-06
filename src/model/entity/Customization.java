package model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;

/**
 * Created by amir on 6/11/17.
 */
@Entity
@Table
public class Customization {
    @Id
    @GeneratedValue
    private long id;
    private int totalNotification = 10;
    private int timeNotification = 5*60*60*1000;
    private ArrayList<String> priorityNotification;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTotalNotification() {
        return totalNotification;
    }

    public void setTotalNotification(int totalNotification) {
        this.totalNotification = totalNotification;
    }

    public int getTimeNotification() {
        return timeNotification;
    }

    public void setTimeNotification(int timeNotification) {
        this.timeNotification = timeNotification;
    }

    public ArrayList<String> getPriorityNotification() {
        return priorityNotification;
    }

    public void setPriorityNotification(ArrayList<String> priorityNotification) {
        this.priorityNotification = priorityNotification;
    }
}
