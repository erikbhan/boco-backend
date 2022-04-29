package no.ntnu.idatt2106.model.DTO;

import java.util.List;

public class ListingWithUnavailabilityDTO {
    private int listingID;
    private String title;
    private String description;
    private double pricePerDay;
    private String address;
    private int userID;
    private String[] categoryNames;
    private int[] communityIDs;
    private List<List<Long>> unavailabilityDates;

    public ListingWithUnavailabilityDTO(String title, String description, double pricePerDay, String address, int userID, String[] categoryNames, int[] communityIDs, List<List<Long>> unavailabilityDates) {
        this.title = title;
        this.description = description;
        this.pricePerDay = pricePerDay;
        this.address = address;
        this.userID = userID;
        this.categoryNames = categoryNames;
        this.communityIDs = communityIDs;
        this.unavailabilityDates = unavailabilityDates;
    }

    public ListingWithUnavailabilityDTO() {

    }

    public int getListingID() {
        return listingID;
    }

    public void setListingID(int listingID) {
        this.listingID = listingID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String[] getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(String[] categoryNames) {
        this.categoryNames = categoryNames;
    }

    public int[] getCommunityIDs() {
        return communityIDs;
    }

    public void setCommunityIDs(int[] communityIDs) {
        this.communityIDs = communityIDs;
    }

    public List<List<Long>> getUnavailabilityDates() {
        return unavailabilityDates;
    }

    public void setUnavailabilityDates(List<List<Long>> unavailabilityDates) {
        this.unavailabilityDates = unavailabilityDates;
    }
}
