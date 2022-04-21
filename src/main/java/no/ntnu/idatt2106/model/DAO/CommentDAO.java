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
@Table(name = "Comment")
public class CommentDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private int commentID;
    @Column(name= "text")
    private String text;
    @ManyToOne
    @JoinColumn(name = "feed_entry_Id")
    private int feedEntryID;

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

    public int getFeedEntryID() {
        return this.feedEntryID;
    }

    public void setFeedEntryID(int feedEntryID) {
        this.feedEntryID = feedEntryID;
    }
}