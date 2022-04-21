package no.ntnu.idatt2106.model.ID;

import java.io.Serializable;

/**
 * Class for creating a composite primary key for the junction table UserCommunity
 */
public class UserCommunityID implements Serializable {
    private long userID;
    private long communityID;

    public UserCommunityID(long userID, long communityID){
        this.userID = userID;
        this.communityID = communityID;
    }

    public UserCommunityID() {
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getCommunityID() {
        return communityID;
    }

    public void setCommunityID(long communityID) {
        this.communityID = communityID;
    }
}
