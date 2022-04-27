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
    List<RentDAO> findAllByRenterIDAndIsAccepted(UserDAO renterID, boolean isAccepted);
    List<RentDAO> findRentDAOSByRenterID(UserDAO renter);
    List<RentDAO> findRentDAOSByListingOwnerID(ListingDAO listing);
    List<RentDAO> findAllByRenterID(UserDAO renterId);

    List<RentDAO> findAllByListingOwnerID(ListingDAO listingId);

    RentDAO findByRentID(int rentId);
}
