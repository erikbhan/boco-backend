package no.ntnu.idatt2106.model.DAO;

import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name = "Feed_entry", schema = "public")
public class FeedEntryDAO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_entry_id")
    private int feedEntryID;
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
    @JoinColumn(name = "community_Id")
    private CommunityDAO communityID;
    @ManyToOne
    @JoinColumn(name = "notification_id")
    private NotificationDAO notificationID;

    public int getFeedEntryID() {
        return this.feedEntryID;
    }

    public void setFeedEntryID(int FeedEntryID) {
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

    public CommunityDAO getCommunityID() {
        return communityID;
    }

    public void setCommunityID(CommunityDAO communityID) {
        this.communityID = communityID;
    }

    public NotificationDAO getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(NotificationDAO notificationID) {
        this.notificationID = notificationID;
    }
}
