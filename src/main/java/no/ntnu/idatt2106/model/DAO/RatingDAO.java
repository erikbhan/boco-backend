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
    private int score;
    @Column(name = "comment")
    private String comment;
    @Column(name= "renter_is_receiver_of_rating")
    private boolean renterIsReceiverOfRating;
    @ManyToOne
    @JoinColumn(name = "rent_id")
    private RentDAO rentID;

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

    public RentDAO getRentID() {
        return rentID;
    }

    public void setRentID(RentDAO rentID) {
        this.rentID = rentID;
    }
}