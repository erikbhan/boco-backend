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
    @ManyToOne
    @JoinColumn(name = "notification_id")
    private NotificationDAO notificationID;


    public int getRentID() {
        return this.rentID;
    }

    public void setRentID(int rent_id) {
        this.rentID = rent_id;
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

    public boolean isIsAccepted() {
        return this.isAccepted;
    }

    public boolean getIsAccepted() {
        return this.isAccepted;
    }

    public void setIsAccepted(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

}