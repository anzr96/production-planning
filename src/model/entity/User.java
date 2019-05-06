package model.entity;

import controller.StaticController;
import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.List;

/**
 * Created by amir on 1/24/17.
 */
@Entity
@Table(name = "USERS")
public class User implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String password;
    @Column(columnDefinition = StaticController.stringType)
    private String name;
    @Column(columnDefinition = "LONG RAW")
    private PublicKey publicKey;
    @Column(name = "dates")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "connection")
    private boolean connect;
    private boolean rememberMe;
    @JoinColumn(name = "fk_custom", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Customization customization;
    @JoinColumn(name = "fk_mail_sent", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_MAIL_SENT")
    private List<Mail> mailsSent;
    @JoinColumn(name = "fk_mail_receive", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_MAIL_RECEIVE")
    private List<Mail> mailsReceive;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customization getCustomization() {
        return customization;
    }

    public void setCustomization(Customization customization) {
        this.customization = customization;
    }

    public List<Mail> getMailsSent() {
        return mailsSent;
    }

    public void setMailsSent(List<Mail> mailsSent) {
        this.mailsSent = mailsSent;
    }

    public List<Mail> getMailsReceive() {
        return mailsReceive;
    }

    public void setMailsReceive(List<Mail> mailsReceive) {
        this.mailsReceive = mailsReceive;
    }

    public boolean isConnect() {
        return connect;
    }

    public void setConnect(boolean connect) {
        this.connect = connect;
    }
}
