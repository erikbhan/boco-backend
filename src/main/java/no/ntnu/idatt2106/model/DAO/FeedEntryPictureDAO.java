package no.ntnu.idatt2106.model.DAO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "public.Feed_entry_picture")
public class FeedEntryPictureDAO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_picture_id")
    private long feedPictureID;
    @Column(name = "picture")
    private String picture;
    // @ManyToOne
    // @JoinColumn(name = "feed_entry_Id")
    private int feedEntryID;

    public long getFeedPictureID() {
        return this.feedPictureID;
    }

    public void setFeedPictureID(long feedPictureID) {
        this.feedPictureID = feedPictureID;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getFeedEntryID() {
        return this.feedEntryID;
    }

    public void setFeedEntryID(int feedEntryID) {
        this.feedEntryID = feedEntryID;
    }
}