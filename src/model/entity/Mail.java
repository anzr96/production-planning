package model.entity;

import controller.StaticController;
import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by amir on 7/21/17.
 */
@Entity
@Table
public class Mail {
    @Id
    @GeneratedValue
    private long id;
    private String sender;
    private String receiver;
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentDate;
    @Column(columnDefinition = StaticController.stringType)
    private String subject;
    @Column(columnDefinition = StaticController.stringType)
    private String description;
    @Column(columnDefinition = StaticController.stringType)
    private String attach;
    @JoinColumn(name = "fk_attach_file", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_ATTACH_FILE")
    private List<AttachFile> attachFiles;
    private boolean seen = false;
    @JoinColumn(name = "fk_mail", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Mail replyMail;

    public Mail() {
        attachFiles = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public List<AttachFile> getAttachFiles() {
        return attachFiles;
    }

    public void setAttachFiles(List<AttachFile> attachFiles) {
        this.attachFiles = attachFiles;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public Mail getReplyMail() {
        return replyMail;
    }

    public void setReplyMail(Mail replyMail) {
        this.replyMail = replyMail;
    }
}
