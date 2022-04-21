package no.ntnu.idatt2106.model.DAO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "listing_category")
public class ListingCategoryDAO {
    @Id
    private long listingID;


    public long getListingID() {
        return listingID;
    }

    public void setListingID(long listingID) {
        this.listingID = listingID;
    }

}
