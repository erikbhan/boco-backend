package no.ntnu.idatt2106.model.DAO;
import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name = "Rent", schema = "public")
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
    @ManyToOne
    @JoinColumn(name = "listing_owner_id")
    private ListingDAO listingOwnerID;
    @ManyToOne
    @JoinColumn(name = "renter_id")
    private UserDAO renterID;

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

    public boolean isAccepted() {
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

}