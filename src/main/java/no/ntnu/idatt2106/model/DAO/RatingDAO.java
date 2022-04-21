package no.ntnu.idatt2106.model.DAO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Rating")
public class RatingDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rating_id")
    private int ratingID;
    @Column(name= "score")
    private int score;
    @Column(name = "comment")
    private String comment;
    @Column(name= "renter_is_reciever_of_rating")
    private boolean renterIsRecieverOfRating;


    public int getRatingID() {
        return this.ratingID;
    }

    public void setRatingID(int ratingID) {
        this.ratingID = ratingID;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isRenterIsRecieverOfRating() {
        return this.renterIsRecieverOfRating;
    }

    public boolean getRenterIsRecieverOfRating() {
        return this.renterIsRecieverOfRating;
    }

    public void setRenterIsRecieverOfRating(boolean renterIsRecieverOfRating) {
        this.renterIsRecieverOfRating = renterIsRecieverOfRating;
    }
}