package no.ntnu.idatt2106.model.DTO;

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
    int renterId;

    public RentDTO(long fromTime, long toTime, Boolean isAccepted, int listingId, int renterId) {
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.isAccepted = isAccepted;
        this.listingId = listingId;
        this.renterId = renterId;
    }

    public RentDTO(int rentId, long fromTime, long toTime, Boolean isAccepted, int listingId, int renterId) {
        this.rentId = rentId;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.isAccepted = isAccepted;
        this.listingId = listingId;
        this.renterId = renterId;
    }

    public RentDTO(RentDAO rentDAO) {
        this.rentId = rentDAO.getRentID();
        this.fromTime = rentDAO.getFromTime();
        this.toTime = rentDAO.getToTime();
        this.isAccepted = rentDAO.getIsAccepted();
        this.listingId = rentDAO.getListingOwnerID().getListingID();
        this.renterId = rentDAO.getRenterID().getUserID();
    }

    public RentDTO() {}

    public int getRentId() {return rentId;}

    public long getFromTime() {
        return fromTime;
    }

    public long getToTime() {
        return toTime;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public int getListingId() {
        return listingId;
    }

    public int getRenterId() {
        return renterId;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setRentId(int rentId) {this.rentId = rentId;}

    public void setFromTime(long fromTime) {
        this.fromTime = fromTime;
    }

    public void setToTime(long toTime) {
        this.toTime = toTime;
    }

    public void setAccepted(Boolean accepted) {
        this.isAccepted = accepted;
    }

    public void setListingId(int listingId) {
        this.listingId = listingId;
    }

    public void setRenterId(int renterId) {
        this.renterId = renterId;
    }
}
