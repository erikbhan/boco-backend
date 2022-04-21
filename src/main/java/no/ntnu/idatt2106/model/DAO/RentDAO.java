package no.ntnu.idatt2106.model.DAO;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "public.Rent")
public class RentDAO{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rent_id")
    private long rentID;
    @Column(name = "from_time")
    private Date fromTime;
    @Column(name = "to_time")
    private Date toTime;
    @Column(name = "is_accepted")
    private boolean isAccepted;


    public long getRentID() {
        return this.rentID;
    }

    public void setRentID(long rent_id) {
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