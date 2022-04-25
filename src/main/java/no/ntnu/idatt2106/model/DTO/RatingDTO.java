package no.ntnu.idatt2106.model.DTO;

public class RatingDTO {

    public int score;
    public String comment;

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
}
