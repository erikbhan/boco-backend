package no.ntnu.idatt2106.model.ID;

import java.io.Serializable;

/**
 * Class for creating a composite primary key for the junction table GroupListing
 */
public class GroupListingID implements Serializable {
    private long groupID;
    private long listingID;

    public GroupListingID(long groupID, long listingID){
        this.groupID = groupID;
        this.listingID = listingID;
    }

    public long getGroupID() {
        return groupID;
    }

    public long getListingID() {
        return listingID;
    }

    public void setListingID(long listingID) {
        this.listingID = listingID;
    }

    public void setGroupID(long groupID) {
        this.groupID = groupID;
    }
}
