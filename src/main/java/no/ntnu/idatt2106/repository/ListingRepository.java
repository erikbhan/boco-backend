package no.ntnu.idatt2106.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import no.ntnu.idatt2106.model.DAO.ListingDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;


/**
 * A class meant to access the listing table in the DB.
 * This class contains some premade methods and some custom-made ones.
 */
@Repository
public interface ListingRepository extends JpaRepository<ListingDAO, Integer> {
    //Finds all listings of one user
    List<ListingDAO> findAllFromListingDAOByUserID(UserDAO userID);
    ListingDAO findListingDAOByListingID(int listingId);
    List<ListingDAO> findAllByUserID(UserDAO userId);
    List<ListingDAO> findListingDAOSByUserID(UserDAO user);;
}
