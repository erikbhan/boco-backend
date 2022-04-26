package no.ntnu.idatt2106.model.DAO;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class functions as a representation of the table notification in the DB.
 * All fields in the notification table is represented in this class, with access methods for everyone.
 */
@Entity
@Table(name = "notification", schema = "public")
public class NotificationDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private int notificationID;
    @Column(name= "is_seen")
    private boolean isSeen;
    @Column(name= "created_time")
    private Date createdTime;

    public int getNotificationID() {
        return this.notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public boolean isIsSeen() {
        return this.isSeen;
    }

    public boolean getIsSeen() {
        return this.isSeen;
    }

    public void setIsSeen(boolean isSeen) {
        this.isSeen = isSeen;
    }

    public Date getCreatedTime() {
        return this.createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}