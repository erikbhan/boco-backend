package no.ntnu.idatt2106.model.DAO;

import org.apache.catalina.User;

import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name = "public.Feed_entry")
public class FeedEntryDAO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_entry_id")
    private long feedEntryID;
    @Column(name = "title")
    private String title;
    @Column(name = "text")
    private String text;
    @Column(name = "is_request")
    private Boolean isRequest;
    @Column(name = "time_posted")
    private Date timePosted;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDAO userID;
    @ManyToOne
    @JoinColumn(name = "group_Id")
    private GroupDAO groupID;
    @ManyToOne
    @JoinColumn(name = "notification_id")
    private NotificationDAO notificationID;

    public long getFeedEntryID() {
        return this.feedEntryID;
    }

    public void setFeedEntryID(long FeedEntryID) {
        this.feedEntryID = FeedEntryID;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean isIsRequest() {
        return this.isRequest;
    }

    public Boolean getIsRequest() {
        return this.isRequest;
    }

    public void setIsRequest(Boolean isRequest) {
        this.isRequest = isRequest;
    }

    public Date getTimePosted() {
        return this.timePosted;
    }

    public void setTimePosted(Date timePosted) {
        this.timePosted = timePosted;
    }

    public Boolean getRequest() {
        return isRequest;
    }

    public void setRequest(Boolean request) {
        isRequest = request;
    }

    public UserDAO getUserID() {
        return userID;
    }

    public void setUserID(UserDAO userID) {
        this.userID = userID;
    }

    public GroupDAO getGroupID() {
        return groupID;
    }

    public void setGroupID(GroupDAO groupID) {
        this.groupID = groupID;
    }

    public NotificationDAO getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(NotificationDAO notificationID) {
        this.notificationID = notificationID;
    }
}
