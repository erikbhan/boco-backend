package no.ntnu.idatt2106.model.ID;

import java.io.Serializable;

/**
 * Class for creating a composite primary key for the junction table CommunityListing
 */
public class CommunityListingID implements Serializable {
    private long communityID;
    private long listingID;

    public CommunityListingID(long communityID, long listingID){
        this.communityID = communityID;
        this.listingID = listingID;
    }

    public long getCommunityID() {
        return communityID;
    }

    public long getListingID() {
        return listingID;
    }

    public void setListingID(long listingID) {
        this.listingID = listingID;
    }

    public void setCommunityID(long communityID) {
        this.communityID = communityID;
    }
}
