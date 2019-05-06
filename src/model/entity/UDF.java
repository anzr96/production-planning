package model.entity;

import controller.StaticController;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by amir on 7/24/17.
 */
@Entity
@Table
public class UDF {
    @Id
    @GeneratedValue
    private long id;
    @Column(columnDefinition = StaticController.stringType)
    private String name;
    private String type;
    @Column(columnDefinition = StaticController.stringType)
    private String string;
    @Column(name = "dates")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "nmbr")
    private double number;
    private byte[] bytes;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
