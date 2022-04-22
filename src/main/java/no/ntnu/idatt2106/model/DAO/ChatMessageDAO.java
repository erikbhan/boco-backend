package no.ntnu.idatt2106.model.DAO;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Chat_message", schema = "public")
public class ChatMessageDAO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private long messageID;
    @Column(name = "text")
    private String text;
    @Column(name = "time_sent")
    private Date timeSent;
    @Column(name = "is_read")
    private Boolean isRead;
    @ManyToOne
    @JoinColumn(name = "sending_user_id")
    private UserDAO sendingUserID;
    @ManyToOne
    @JoinColumn(name = "receiving_user_id")
    private UserDAO receivingUserID;

    public long getMessageID() {
        return this.messageID;
    }

    public void setMessageID(long messageID) {
        this.messageID = messageID;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimeSent() {
        return this.timeSent;
    }

    public void setTimeSent(Date timeSent) {
        this.timeSent = timeSent;
    }

    public Boolean isIsRead() {
        return this.isRead;
    }

    public Boolean getIsRead() {
        return this.isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public UserDAO getSendingUserID() {
        return sendingUserID;
    }

    public void setSendingUserID(UserDAO sendingUserID) {
        this.sendingUserID = sendingUserID;
    }

    public UserDAO getReceivingUserID() {
        return receivingUserID;
    }

    public void setReceivingUserID(UserDAO receivingUserID) {
        this.receivingUserID = receivingUserID;
    }
}
