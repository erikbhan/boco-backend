package no.ntnu.idatt2106.model.DAO;
import no.ntnu.idatt2106.model.DTO.RentDTO;

import java.sql.Date;

import javax.persistence.*;

/**
 * This class functions as a representation of the table rent in the DB.
 * All fields in the rent table is represented in this class, with access methods for everyone.
 */
@Entity
@Table(name = "rent", schema = "public")
public class RentDAO{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rent_id")
    private int rentID;
    @Column(name = "from_time")
    private Date fromTime;
    @Column(name = "to_time")
    private Date toTime;
    @Column(name = "is_accepted")
    private boolean isAccepted;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @ManyToOne
    @JoinColumn(name = "listing_owner_id")
    private ListingDAO listingOwnerID;
    @ManyToOne
    @JoinColumn(name = "renter_id")
    private UserDAO renterID;
    @ManyToOne
    @JoinColumn(name = "notification_id")
    private NotificationDAO notificationID;

    public RentDAO(Date fromTime, Date toTime, boolean isAccepted, ListingDAO listingOwnerID, UserDAO renterID, NotificationDAO notificationID) {
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.isAccepted = isAccepted;
        this.listingOwnerID = listingOwnerID;
        this.renterID = renterID;
        this.notificationID = notificationID;
    }

    public RentDAO(RentDTO rentDTO) {
        this.fromTime = rentDTO.getFromTime();
        this.toTime = rentDTO.getToTime();
        this.isAccepted = rentDTO.getIsAccepted();
    }

    public RentDAO() {
    }

    public int getRentID() {
        return rentID;
    }

    public void setRentID(int rentID) {
        this.rentID = rentID;
    }

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

    public boolean getIsAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public ListingDAO getListingOwnerID() {
        return listingOwnerID;
    }

    public void setListingOwnerID(ListingDAO listingOwnerID) {
        this.listingOwnerID = listingOwnerID;
    }

    public UserDAO getRenterID() {
        return renterID;
    }

    public void setRenterID(UserDAO renterID) {
        this.renterID = renterID;
    }

    public NotificationDAO getNotificationID() {return notificationID;}

    public void setNotificationID(NotificationDAO notificationID) {this.notificationID = notificationID;}

    public boolean isDeleted() {return isDeleted;}

    public void setDeleted(boolean deleted) {isDeleted = deleted;}
}