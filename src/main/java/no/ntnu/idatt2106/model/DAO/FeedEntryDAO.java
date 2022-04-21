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
@Table(name = "Feed_entry")
public class FeedEntryDAO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    // @ManyToOne
    // @JoinColumn(name = "user_id")
    private int userID;
    // @ManyToOne
    // @JoinColumn(name = "group_Id")
    private int groupID;

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

    public int getUserID() {
        return this.userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getGroupID() {
        return this.groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }
}
