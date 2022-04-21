package no.ntnu.idatt2106.model.DAO;

import javax.persistence.*;

@Entity
@Table(name = "Listing_picture", schema = "public")
public class ListingPictureDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "listing_picture_id")
    private long listingPicureID;
    @Column(name= "picture")
    private String picture;
    @ManyToOne
    @JoinColumn(name = "listing_id")
    private ListingDAO listingID;

    public long getListingPicureID() {
        return this.listingPicureID;
    }

    public void setListingPicureID(long listingPicureID) {
        this.listingPicureID = listingPicureID;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}