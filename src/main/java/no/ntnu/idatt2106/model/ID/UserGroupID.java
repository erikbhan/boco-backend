package no.ntnu.idatt2106.model.ID;

import java.io.Serializable;

public class UserGroupID implements Serializable {
    private long userID;
    private long groupID;

    public UserGroupID(long userID, long groupID){
        this.userID = userID;
        this.groupID = groupID;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getGroupID() {
        return groupID;
    }

    public void setGroupID(long groupID) {
        this.groupID = groupID;
    }
}
