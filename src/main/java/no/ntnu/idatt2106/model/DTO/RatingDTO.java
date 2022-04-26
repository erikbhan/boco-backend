package no.ntnu.idatt2106.model.DTO;

public class RatingDTO {

    int score;
    String comment;
    boolean renterIsReceiverOfRating;
    int rentID;

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
