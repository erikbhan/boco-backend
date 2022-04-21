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
@Table(name = "Chat_message")
public class ChatMessageDAO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "message_id")
    private int messageID;
    @Column(name = "text")
    private String text;
    @Column(name = "time_sent")
    private Date timeSent;
    @Column(name = "is_read")
    private Boolean isRead;
    //@ManyToOne
    //@JoinColumn(name = "sending_user_id")
    private int seendingUserID;
    // @ManyToOne
    // @JoinColumn(name = "receiving_user_id")
    private int receivingUserID;

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

    public int getSeendingUserID() {
        return this.seendingUserID;
    }

    public void setSeendingUserID(int seendingUserID) {
        this.seendingUserID = seendingUserID;
    }

    public int getReceivingUserID() {
        return this.receivingUserID;
    }

    public void setReceivingUserID(int receivingUserID) {
        this.receivingUserID = receivingUserID;
    }
}
