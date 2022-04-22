package no.ntnu.idatt2106.model.DTO;

public class UserCommunityDTO {
    private int userID;
    private int communityID;

    public UserCommunityDTO(int userID, int communityID) {
        this.userID = userID;
        this.communityID = communityID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getCommunityID() {
        return communityID;
    }

    public void setCommunityID(int communityID) {
        this.communityID = communityID;
    }
}
