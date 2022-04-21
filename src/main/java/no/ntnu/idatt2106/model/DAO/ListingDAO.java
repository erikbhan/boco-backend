package no.ntnu.idatt2106.model.DAO;
import javax.persistence.*;

@Entity
@Table(name = "public.Listing")
public class ListingDAO{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "listing_id")
    private long listingID;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "price_per_day")
    private double pricePerDay;
    @Column(name = "address")
    private String address;
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserDAO userID;

    public long getListingID() {
        return this.listingID;
    }

    public void setListingID(long listingID) {
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

}