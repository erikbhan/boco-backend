package no.ntnu.idatt2106.model.DTO;


public class ListingDTO {
    private String title;
    private String description;
    private double pricePerDay;
    private String address;
    private int userID;
    private String[] categoryNames;

    public ListingDTO() {
    }

    public ListingDTO(String title, String description, double pricePerDay, String address,
            int userID, String[] categoryNames) {
        this.title = title;
        this.description = description;
        this.pricePerDay = pricePerDay;
        this.address = address;
        this.userID = userID;
        this.categoryNames = categoryNames;
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

}
