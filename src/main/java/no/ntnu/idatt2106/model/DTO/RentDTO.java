package no.ntnu.idatt2106.model.DTO;

import no.ntnu.idatt2106.model.DAO.ListingDAO;
import no.ntnu.idatt2106.model.DAO.RentDAO;

import java.sql.Date;

/**
 * A class representing the rent agreement object.
 * This class shall be returned to the frontend instead of the rent dao.
 * This class contains necessary access-methods for the data it contains.
 */
public class RentDTO {
    int rentId;
    long fromTime;
    long toTime;
    Boolean isAccepted;
    int listingId;
    ListingDTO listing;
    int renterId;
    String message;
    int listingOwnerId;
    Long createdAt;
    boolean isDeleted;

    public RentDTO(long fromTime, long toTime, Boolean isAccepted, int listingId, int renterId, boolean isDeleted) {
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.isAccepted = isAccepted;
        this.listingId = listingId;
        this.renterId = renterId;
        this.createdAt = System.currentTimeMillis();
        this.isDeleted = isDeleted;
    }

    public RentDTO(int rentId, long fromTime, long toTime, Boolean isAccepted, int listingId, int renterId) {
        this.rentId = rentId;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.isAccepted = isAccepted;
        this.listingId = listingId;
        this.renterId = renterId;
        this.createdAt = System.currentTimeMillis();
    }

    public RentDTO(RentDAO rentDAO) {
        this.rentId = rentDAO.getRentID();
        this.fromTime = rentDAO.getFromTime();
        this.toTime = rentDAO.getToTime();
        this.isAccepted = rentDAO.getIsAccepted();
        this.listing = new ListingDTO(rentDAO.getListing());
        this.listingId = rentDAO.getListing().getListingID();
        this.renterId = rentDAO.getRenter().getUserID();
        this.createdAt = rentDAO.getCreatedAt();
        this.isDeleted = rentDAO.isDeleted();
        this.message = rentDAO.getMessage();
    }

    public RentDTO(long fromTime, long toTime, Boolean isAccepted, int listingId, int renterId, String message) {
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.isAccepted = isAccepted;
        this.listingId = listingId;
        this.renterId = renterId;
        this.message = message;
        this.createdAt = System.currentTimeMillis();
    }

    public RentDTO() {
        this.createdAt = System.currentTimeMillis();
    }
   
    public int getRentId() {
        return this.rentId;
    }

    public void setRentId(int rentId) {
        this.rentId = rentId;
    }

    public long getFromTime() {
        return this.fromTime;
    }

    public void setFromTime(long fromTime) {
        this.fromTime = fromTime;
    }

    public long getToTime() {
        return this.toTime;
    }

    public void setToTime(long toTime) {
        this.toTime = toTime;
    }

    public Boolean isIsAccepted() {
        return this.isAccepted;
    }

    public Boolean getIsAccepted() {
        return this.isAccepted;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public int getListingId() {
        return this.listingId;
    }

    public void setListingId(int listingId) {
        this.listingId = listingId;
    }

    public int getRenterId() {
        return this.renterId;
    }

    public void setRenterId(int renterId) {
        this.renterId = renterId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ListingDTO getListing() {
        return listing;
    }

    public void setListing(ListingDTO listing) {
        this.listing = listing;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
