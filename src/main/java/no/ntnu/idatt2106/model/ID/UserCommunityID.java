package no.ntnu.idatt2106.model.ID;

import java.io.Serializable;

/**
 * Class for creating a composite primary key for the junction table UserCommunity
 */
public class UserCommunityID implements Serializable {
    private int userID;
    private int communityID;

    public UserCommunityID(int userID, int communityID){
        this.userID = userID;
        this.communityID = communityID;
    }

    public UserCommunityID() {
    }

    public int getUser() {
        return userID;
    }

    public void setUser(int userID) {
        this.userID = userID;
    }

    public int getCommunity() {
        return communityID;
    }

    public void setCommunity(int communityID) {
        this.communityID = communityID;
    }
}
