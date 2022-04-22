package no.ntnu.idatt2106.model.DTO;

import java.sql.Date;

public class RentDTO {

    public Date fromTime;
    public Date toTime;
    public boolean isAccepted;
    public int listingOwnerId;
    public int renterId;

    public Date getFromTime() {
        return fromTime;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public Date getToTime() {
        return toTime;
    }

    public void setToTime(Date toTime) {
        this.toTime = toTime;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public int getListingOwnerId() {
        return listingOwnerId;
    }

    public void setListingOwnerId(int listingOwnerId) {
        this.listingOwnerId = listingOwnerId;
    }

    public int getRenterId() {
        return renterId;
    }

    public void setRenterId(int renterId) {
        this.renterId = renterId;
    }
}
