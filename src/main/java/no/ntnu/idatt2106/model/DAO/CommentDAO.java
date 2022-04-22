package no.ntnu.idatt2106.model.DAO;

import javax.persistence.*;

@Entity
@Table(name = "Comment", schema = "public")
public class CommentDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentID;
    @Column(name= "text")
    private String text;
    @ManyToOne
    @JoinColumn(name = "feed_entry_Id")
    private FeedEntryDAO feedEntryID;

    public int getCommentID() {
        return this.commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public FeedEntryDAO getFeedEntryID() {
        return feedEntryID;
    }

    public void setFeedEntryID(FeedEntryDAO feedEntryID) {
        this.feedEntryID = feedEntryID;
    }
}