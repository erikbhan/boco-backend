package no.ntnu.idatt2106.model.DAO;

import no.ntnu.idatt2106.model.ID.CommunityListingID;

import javax.persistence.*;

/**
 *Table for handling the many to many connection between classes community and Listing
 */
@Entity
@Table(name = "community_listing", schema = "public")
@IdClass(CommunityListingID.class)
public class CommunityListingDAO {
    @Id
    @ManyToOne
    @JoinColumn(name = "community_id")
    private CommunityDAO communityID;
    @Id
    @ManyToOne
    @JoinColumn(name = "listing_id")
    private ListingDAO listingID;

    public CommunityDAO getCommunity() {
        return communityID;
    }

    public void setCommunity(CommunityDAO communityID) {
        this.communityID = communityID;
    }

    public ListingDAO getListing() {
        return listingID;
    }

    public void setListing(ListingDAO listingID) {
        this.listingID = listingID;
    }
}
