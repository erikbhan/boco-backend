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

    public long getCommunityID() {
        return communityID;
    }

    public long getListingID() {
        return listingID;
    }

    public void setListingID(int listingID) {
        this.listingID = listingID;
    }

    public void setCommunityID(int communityID) {
        this.communityID = communityID;
    }
}
