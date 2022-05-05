package no.ntnu.idatt2106.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    List<ListingDAO> findAllFromListingDAOByUser(UserDAO userID);
    ListingDAO findListingDAOByListingID(int listingId);
    List<ListingDAO> findAllByUserAndDeletedIsFalse(UserDAO userId);
    List<ListingDAO> findListingDAOSByUser(UserDAO user);
    List<ListingDAO> findAllByDeletedIsFalse();


    //Finds all listings with title containing a phrase
    @Query("SELECT m FROM ListingDAO m WHERE m.title LIKE %:title%")     
    List<ListingDAO> findAllByTitleLike(@Param("title") String title);

    @Query(value = "SELECT * FROM listing WHERE (listing_id IN(SELECT MAX(listing_id) FROM listing WHERE user_id=?1))", nativeQuery = true)
    ListingDAO findLastAddedListingByUser (int userID);
}
