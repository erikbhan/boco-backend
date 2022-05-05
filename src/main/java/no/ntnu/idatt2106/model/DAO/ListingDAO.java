package no.ntnu.idatt2106.model.DAO;
import javax.persistence.*;

/**
 * This class functions as a representation of the table listing in the DB.
 * All fields in the listing table is represented in this class, with access methods for everyone.
 */
@Entity
@Table(name = "listing", schema = "public")
public class ListingDAO{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "listing_id")
    private int listingID;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "price_per_day")
    private double pricePerDay = 1;
    @Column(name = "address")
    private String address;
    @Column(name = "deleted")
    private boolean deleted = false;
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserDAO user;

    public int getListingID() {
        return this.listingID;
    }

    public void setListingID(int listingID) {
        this.listingID = listingID;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPricePerDay() {
        return this.pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public UserDAO getUser() {
        return this.user;
    }

    public void setUser(UserDAO user) {
        this.user = user;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
}