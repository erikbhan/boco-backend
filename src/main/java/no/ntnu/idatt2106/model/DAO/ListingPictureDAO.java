package no.ntnu.idatt2106.model.DAO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Listing_picture")
public class ListingPictureDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "listing_picture_id")
    private int listingPicureID;
    @Column(name= "picture")
    private String picture;

    public int getListingPicureID() {
        return this.listingPicureID;
    }

    public void setListingPicureID(int listingPicureID) {
        this.listingPicureID = listingPicureID;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}