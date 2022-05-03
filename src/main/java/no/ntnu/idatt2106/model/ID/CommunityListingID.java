package no.ntnu.idatt2106.model.ID;

import java.io.Serializable;

/**
 * Class for creating a composite primary key for the junction table CommunityListing
 */
public class CommunityListingID implements Serializable {
    private int communityID;
    private int listingID;

    public CommunityListingID(){
        
    }
    public CommunityListingID(int communityID, int listingID){
        this.communityID = communityID;
        this.listingID = listingID;
    }

    public long getCommunity() {
        return communityID;
    }

    public long getListing() {
        return listingID;
    }

    public void setListing(int listingID) {
        this.listingID = listingID;
    }

    public void setCommunity(int communityID) {
        this.communityID = communityID;
    }
}
