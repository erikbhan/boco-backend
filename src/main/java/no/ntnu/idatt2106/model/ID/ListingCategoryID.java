package no.ntnu.idatt2106.model.ID;

import java.io.Serializable;

/**
 * Class for creating a composite primary key for the junction table Listing Category
 */

public class ListingCategoryID implements Serializable {
    private int listingID;
    private int categoryID;

    public ListingCategoryID(){
        
    }
    public ListingCategoryID(int listingID, int categoryID){
        this.listingID = listingID;
        this.categoryID = categoryID;
    }

    public int getListingID() {
        return listingID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public void setListingID(int listingID) {
        this.listingID = listingID;
    }
}
