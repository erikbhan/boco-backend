package no.ntnu.idatt2106.model.DTO;

public class RatingDTO {

    private int ratingID;
    private int score;
    String comment;
    boolean renterIsReceiverOfRating;
    private int rentID;

    public RatingDTO() {
    }

    public RatingDTO(int score, String comment, boolean renterIsReceiverOfRating, int rentID) {
        this.score = score;
        this.comment = comment;
        this.renterIsReceiverOfRating = renterIsReceiverOfRating;
        this.rentID = rentID;
    }

    public RatingDTO(int ratingID, int score, String comment, boolean renterIsReceiverOfRating, int rentID) {
        this.ratingID = ratingID;
        this.score = score;
        this.comment = comment;
        this.renterIsReceiverOfRating = renterIsReceiverOfRating;
        this.rentID = rentID;
    }

    public int getRatingID() {
        return ratingID;
    }

    public void setRatingID(int ratingID) {
        this.ratingID = ratingID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isRenterReceiverOfRating() {
        return renterIsReceiverOfRating;
    }

    public void setRenterIsReceiverOfRating(boolean renterIsReceiverOfRating) {
        this.renterIsReceiverOfRating = renterIsReceiverOfRating;
    }

    public int getRentID() {
        return rentID;
    }

    public void setRentID(int rentID) {
        this.rentID = rentID;
    }
}
