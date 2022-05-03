package no.ntnu.idatt2106.model.DTO;

import no.ntnu.idatt2106.model.DAO.ListingPictureDAO;

/**
 * DTO class for ListingPicture.
 */
public class ListingPictureDTO {
    private int listingID;
    private String picture;

    public ListingPictureDTO(int listingID, String picture) {
        this.listingID = listingID;
        this.picture = picture;
    }

    public ListingPictureDTO(ListingPictureDAO listingPictureDAO) {
        this.listingID = listingPictureDAO.getListingPictureID();
        this.picture = listingPictureDAO.getPicture();
    }

    public ListingPictureDTO() {
    }

    public int getListingID() {
        return listingID;
    }

    public void setListingID(int listingID) {
        this.listingID = listingID;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
