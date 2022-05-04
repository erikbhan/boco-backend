package no.ntnu.idatt2106.model.DAO;

import javax.persistence.*;

@Entity
@Table(name = "Listing_picture", schema = "public")
public class ListingPictureDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "listing_picture_id")
    private int listingPictureID;
    @Column(name= "picture")
    private String picture;
    @ManyToOne
    @JoinColumn(name = "listing_id", nullable = false)
    private ListingDAO listing;

    public ListingPictureDAO(){}

    public ListingPictureDAO(String picture, ListingDAO listing) {
        this.picture = picture;
        this.listing = listing;
    }

    public int getListingPictureID() {
        return this.listingPictureID;
    }

    public void setListingPictureID(int listingPicureID) {
        this.listingPictureID = listingPicureID;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public ListingDAO getListing() {
        return listing;
    }

    public void setListing(ListingDAO listing) {
        this.listing = listing;
    }
}