package no.ntnu.idatt2106.model.DTO;

import java.sql.Date;

public class RentDTO {
    Date fromTime;
    Date toTime;
    Boolean isAccepted;
    int listingId;
    int renterId;
    int notificationId;

    public Date getFromTime() {
        return fromTime;
    }

    public Date getToTime() {
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

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public void setToTime(Date toTime) {
        this.toTime = toTime;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public void setListingId(int listingId) {
        this.listingId = listingId;
    }

    public void setRenterId(int renterId) {
        this.renterId = renterId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }
}
