package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.DAO.ListingDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2106.model.DAO.RentDAO;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<RentDAO, Long> {
    List<RentDAO> findAllByRenterIDAndIsAccepted(UserDAO renterID, boolean isAccepted);

    List<RentDAO> findAllByRenterID(UserDAO renterId);

    List<RentDAO> findAllByListingOwnerID(ListingDAO listingId);

    RentDAO findByRentID(int rentId);
}
