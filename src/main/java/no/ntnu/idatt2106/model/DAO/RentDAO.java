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
    private long fromTime;
    @Column(name = "to_time")
    private long toTime;
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

    public RentDAO(long fromTime, long toTime, boolean isAccepted, ListingDAO listingOwnerID, UserDAO renterID) {
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.fromThyme = fromThyme;
        this.isAccepted = isAccepted;
        this.listingOwnerID = listingOwnerID;
        this.renterID = renterID;
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

    public boolean isDeleted() {return isDeleted;}

    public void setDeleted(boolean deleted) {isDeleted = deleted;}

    public long getFromThyme() {
        return fromThyme;
    }

    public void setFromThyme(long fromThyme) {
        this.fromThyme = fromThyme;
    }
}