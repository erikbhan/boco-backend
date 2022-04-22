package no.ntnu.idatt2106.model.DAO;

import no.ntnu.idatt2106.model.ID.ListingCategoryID;

import javax.persistence.*;

/**
 *Junction table for handling the many to many connection between classes Listing and Category
 */
@Entity
@Table(name = "listing_category", schema = "public")
@IdClass(ListingCategoryID.class)
public class ListingCategoryDAO {
    @Id
    @ManyToOne
    @JoinColumn(name = "listing_id")
    private ListingDAO listingID;
    @Id
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryDAO categoryID;

    public ListingDAO getListingID() {
        return listingID;
    }

    public void setListingID(ListingDAO listingID) {
        this.listingID = listingID;
    }

    public CategoryDAO getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(CategoryDAO categoryID) {
        this.categoryID = categoryID;
    }
}
