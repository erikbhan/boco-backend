package no.ntnu.idatt2106.model.DAO;
import no.ntnu.idatt2106.model.DTO.RentDTO;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

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
    private long fromTime = System.currentTimeMillis();
    @Column(name = "to_time")
    private long toTime = System.currentTimeMillis();
    @Column(name = "is_accepted")
    private boolean isAccepted = false;
    @Column(name = "is_deleted")
    private boolean isDeleted = false;
    @Column(name = "message")
    private String message;
    @Column (name = "created_at", nullable = false)
    private long createdAt = System.currentTimeMillis();
    @ManyToOne
    @JoinColumn(name = "listing_owner_id", nullable = false)
    private ListingDAO listing;
    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    private UserDAO renter;

    public RentDAO(long fromTime, long toTime, boolean isAccepted, ListingDAO listing, UserDAO renter, boolean isDeleted) {
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.isAccepted = isAccepted;
        this.listing = listing;
        this.renter = renter;
        this.createdAt = System.currentTimeMillis();
        this.isDeleted = isDeleted;
    }

    public RentDAO(RentDTO rentDTO) {
        this.fromTime = rentDTO.getFromTime();
        this.toTime = rentDTO.getToTime();
        this.isAccepted = rentDTO.getIsAccepted();
        this.message = rentDTO.getMessage();
        this.createdAt = rentDTO.getCreatedAt();
        this.isDeleted = rentDTO.isDeleted();
    }

    public RentDAO() {
        this.createdAt = System.currentTimeMillis();
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public int getRentID() {
        return rentID;
    }

    public void setRentID(int rentID) {
        this.rentID = rentID;
    }

    public long getFromTime() {
        return fromTime;
    }

    public void setFromTime(long fromTime) {
        this.fromTime = fromTime;
    }

    public long getToTime() {
        return toTime;
    }

    public void setToTime(long toTime) {
        this.toTime = toTime;
    }

    public boolean getIsAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public ListingDAO getListing() {
        return listing;
    }

    public void setListing(ListingDAO listingOwner) {
        this.listing = listingOwner;
    }

    public UserDAO getRenter() {
        return renter;
    }

    public void setRenter(UserDAO renter) {
        this.renter = renter;
    }

    public boolean isDeleted() {return isDeleted;}

    public void setDeleted(boolean deleted) {isDeleted = deleted;}


    @Override
    public String toString() {
        return "RentDAO{" +
                "rentID=" + rentID +
                ", fromTime=" + fromTime +
                ", toTime=" + toTime +
                ", isAccepted=" + isAccepted +
                ", isDeleted=" + isDeleted +
                ", message='" + message + '\'' +
                ", listingOwnerID=" + listing +
                ", renterID=" + renter +
                '}';
    }
}