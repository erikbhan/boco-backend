package no.ntnu.idatt2106.model.DAO;

import no.ntnu.idatt2106.model.ID.GroupListingID;

import javax.persistence.*;

/**
 *Table for handling the many to many connection between classes Group and Listing
 */
@Entity
@Table(name = "public.group_listing")
@IdClass(GroupListingID.class)
public class GroupListingDAO {
    @Id
    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupDAO groupID;
    @Id
    @ManyToOne
    @JoinColumn(name = "listing_id")
    private ListingDAO listingID;

    public GroupDAO getGroupID() {
        return groupID;
    }

    public void setGroupID(GroupDAO groupID) {
        this.groupID = groupID;
    }

    public ListingDAO getListingID() {
        return listingID;
    }

    public void setListingID(ListingDAO listingID) {
        this.listingID = listingID;
    }
}
