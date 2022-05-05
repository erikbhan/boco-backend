package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.DAO.ListingDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2106.model.DAO.RentDAO;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * A class meant to access the rent table in the DB.
 * This class contains some premade methods and some custom-made ones.
 */
@Repository
public interface RentRepository extends JpaRepository<RentDAO, Long> {
    List<RentDAO> findAllByRenterAndIsAccepted(UserDAO renterID, boolean isAccepted);
    List<RentDAO> findRentDAOSByRenter(UserDAO renter);
    List<RentDAO> findRentDAOSByListing(ListingDAO listing);
    List<RentDAO> findAllByRenter(UserDAO renterId);
    List<RentDAO> findAllByListing(ListingDAO listingId);

    RentDAO findByRentID(int rentId);
    List<RentDAO> findAllByListing_User_UserID(int userID);
    List<RentDAO> findAllByRenterOrListing_User(UserDAO renter, UserDAO listing_user);

}
