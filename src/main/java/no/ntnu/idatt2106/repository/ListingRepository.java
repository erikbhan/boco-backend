package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.DAO.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2106.model.DAO.ListingDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A class meant to access the listing table in the DB.
 * This class contains some premade methods and some custom-made ones.
 */
@Repository
public interface ListingRepository extends JpaRepository<ListingDAO, Integer> {
    ListingDAO findListingDAOByListingID(int listingId);

    List<ListingDAO> findAllByUserID(UserDAO userId);
}
