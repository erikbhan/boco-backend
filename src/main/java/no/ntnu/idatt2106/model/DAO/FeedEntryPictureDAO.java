package no.ntnu.idatt2106.model.DAO;

import javax.persistence.*;

@Entity
@Table(name = "Feed_entry_picture", schema = "public")
public class FeedEntryPictureDAO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_picture_id")
    private int feedPictureID;
    @Column(name = "picture")
    private String picture;
    @ManyToOne
    @JoinColumn(name = "feed_entry_Id", nullable = false)
    private FeedEntryDAO feedEntry;

    public int getFeedPictureID() {
        return this.feedPictureID;
    }

    public void setFeedPictureID(int feedPictureID) {
        this.feedPictureID = feedPictureID;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public FeedEntryDAO getFeedEntry() {
        return feedEntry;
    }

    public void setFeedEntry(FeedEntryDAO feedEntryID) {
        this.feedEntry = feedEntryID;
    }
}