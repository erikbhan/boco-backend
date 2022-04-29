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
    Date fromTime;
    Date toTime;
    Boolean isAccepted;
    int listingId;
    int renterId;
    int notificationId;

    public RentDTO(Date fromTime, Date toTime, Boolean isAccepted, int listingId, int renterId) {
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.isAccepted = isAccepted;
        this.listingId = listingId;
        this.renterId = renterId;
    }

    public RentDTO(int rentId, Date fromTime, Date toTime, Boolean isAccepted, int listingId, int renterId, int notificationId) {
        this.rentId = rentId;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.isAccepted = isAccepted;
        this.listingId = listingId;
        this.renterId = renterId;
        this.notificationId = notificationId;
    }

    public RentDTO(RentDAO rentDAO) {
        this.rentId = rentDAO.getRentID();
        this.fromTime = rentDAO.getFromTime();
        this.toTime = rentDAO.getToTime();
        this.isAccepted = rentDAO.getIsAccepted();
        this.listingId = rentDAO.getListingOwnerID().getListingID();
        this.renterId = rentDAO.getRenterID().getUserID();
        if(rentDAO.getNotificationID() != null) {
            this.notificationId = rentDAO.getNotificationID().getNotificationID();
        }
    }

    public RentDTO() {}
   

    public int getRentId() {
        return this.rentId;
    }

    public void setRentId(int rentId) {
        this.rentId = rentId;
    }

    public Date getFromTime() {
        return this.fromTime;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public Date getToTime() {
        return this.toTime;
    }

    public void setToTime(Date toTime) {
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

    public int getNotificationId() {
        return this.notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

}
