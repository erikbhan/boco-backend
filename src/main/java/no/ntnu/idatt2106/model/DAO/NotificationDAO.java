package no.ntnu.idatt2106.model.DAO;

import java.sql.Date;
import javax.persistence.*;

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
    @ManyToOne
    @JoinColumn(name = "group_request", nullable = false)
    private FeedEntryDAO groupRequestID;

    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public FeedEntryDAO getGroupRequestID() {
        return groupRequestID;
    }

    public void setGroupRequestID(FeedEntryDAO groupRequestID) {
        this.groupRequestID = groupRequestID;
    }
}