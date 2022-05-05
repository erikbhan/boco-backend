package no.ntnu.idatt2106.model.DAO;

import javax.persistence.*;

@Entity
@Table(name = "Rating", schema = "public")
public class RatingDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private int ratingID;
    @Column(name= "score")
    private int score = 3;
    @Column(name = "comment")
    private String comment;
    @Column(name= "renter_is_receiver_of_rating")
    private boolean renterIsReceiverOfRating = false;
    @ManyToOne
    @JoinColumn(name = "rent_id", nullable = false)
    private RentDAO rent;

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

    public boolean isRenterIsReceiverOfRating() {
        return this.renterIsReceiverOfRating;
    }

    public void setRenterIsReceiverOfRating(boolean renterIsReceiverOfRating) {
        this.renterIsReceiverOfRating = renterIsReceiverOfRating;
    }

    public RentDAO getRent() {
        return rent;
    }

    public void setRent(RentDAO rent) {
        this.rent = rent;
    }
}