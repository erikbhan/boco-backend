package no.ntnu.idatt2106.model.DAO;
import no.ntnu.idatt2106.model.DTO.RentDTO;

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
    private long fromTime;
    @Column(name = "to_time")
    private long toTime;
    @Column(name = "is_accepted")
    private boolean isAccepted;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @Column(name = "message")
    private String message;
    @ManyToOne
    @JoinColumn(name = "listing_owner_id", nullable = false)
    private ListingDAO listing;
    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    private UserDAO renter;

    public RentDAO(long fromTime, long toTime, boolean isAccepted, ListingDAO listing, UserDAO renter) {
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.isAccepted = isAccepted;
        this.listing = listing;
        this.renter = renter;
    }

    public RentDAO(RentDTO rentDTO) {
        this.fromTime = rentDTO.getFromTime();
        this.toTime = rentDTO.getToTime();
        this.isAccepted = rentDTO.getAccepted();
    }

    public RentDAO() {
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

    public void setListing(ListingDAO listingOwnerID) {
        this.listing = listingOwnerID;
    }

    public UserDAO getRenter() {
        return renter;
    }

    public void setRenter(UserDAO renterID) {
        this.renter = renterID;
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