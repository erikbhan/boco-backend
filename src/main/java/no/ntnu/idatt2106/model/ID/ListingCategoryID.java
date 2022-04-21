package no.ntnu.idatt2106.model.ID;

import java.io.Serializable;

public class ListingCategoryID implements Serializable {
    private long listingID;
    private long categoryID;

    public ListingCategoryID(long listingID, long categoryID){
        this.listingID = listingID;
        this.categoryID = categoryID;
    }

    public long getListingID() {
        return listingID;
    }

    public long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(long categoryID) {
        this.categoryID = categoryID;
    }

    public void setListingID(long listingID) {
        this.listingID = listingID;
    }
}
