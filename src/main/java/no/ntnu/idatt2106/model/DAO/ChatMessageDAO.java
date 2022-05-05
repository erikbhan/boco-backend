package no.ntnu.idatt2106.model.DAO;

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
    private int messageID;
    @Column(name = "text")
    private String text;
    @Column(name = "time_sent")
    private long timeSent = System.currentTimeMillis();
    @Column(name = "is_read")
    private Boolean isRead = false;
    @ManyToOne
    @JoinColumn(name = "sending_user_id", nullable = false)
    private UserDAO sendingUser;
    @ManyToOne
    @JoinColumn(name = "receiving_user_id", nullable = false)
    private UserDAO receivingUser;

    public int getMessageID() {
        return this.messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTimeSent() {
        return this.timeSent;
    }

    public void setTimeSent(long timeSent) {
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

    public UserDAO getSendingUser() {
        return sendingUser;
    }

    public void setSendingUser(UserDAO sendingUser) {
        this.sendingUser = sendingUser;
    }

    public UserDAO getReceivingUser() {
        return receivingUser;
    }

    public void setReceivingUser(UserDAO receivingUser) {
        this.receivingUser = receivingUser;
    }
}
