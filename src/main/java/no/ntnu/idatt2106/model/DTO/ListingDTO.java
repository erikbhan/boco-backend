package no.ntnu.idatt2106.model.DTO;

import no.ntnu.idatt2106.model.DAO.ListingDAO;

/**
 * DTO class for Listing.
 */

public class ListingDTO {
    private int listingID;
    private String title;
    private String description;
    private double pricePerDay;
    private String address;
    private int userID;
    private String[] categoryNames;
    private int[] communityIDs;

    public ListingDTO() {
    }
    /**
     * 
     * @param title
     * @param description
     * @param pricePerDay
     * @param address
     * @param userID
     * @param categoryNames All the categories related to the listing
     * @param communityIDs All the communityIDs related to the listing
     */
    public ListingDTO(String title, String description, double pricePerDay, String address,
            int userID, String[] categoryNames, int[] communityIDs) {
        this.title = title;
        this.description = description;
        this.pricePerDay = pricePerDay;
        this.address = address;
        this.userID = userID;
        this.categoryNames = categoryNames;
        this.communityIDs = communityIDs;
    }

    public ListingDTO(ListingDAO listingDAO) {
        this.listingID = listingDAO.getListingID();
        this.title = listingDAO.getTitle();
        this.description = listingDAO.getDescription();
        this.pricePerDay = listingDAO.getPricePerDay();
        this.address = listingDAO.getAddress();
        this.userID = listingDAO.getUser().getUserID();
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPricePerDay() {
        return this.pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUserID() {
        return this.userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String[] getCategoryNames() {
        return this.categoryNames;
    }

    public void setCategoryNames(String[] categoryNames) {
        this.categoryNames = categoryNames;
    }

    public int[] getCommunityIDs() {
        return this.communityIDs;
    }

    public void setCommunityIDs(int[] communityIDs) {
        this.communityIDs = communityIDs;
    }

    public int getListingID() {return listingID;}

    public void setListingID(int listingID) {
        this.listingID = listingID;
    }
}
