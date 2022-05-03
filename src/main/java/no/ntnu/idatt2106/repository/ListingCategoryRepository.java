package no.ntnu.idatt2106.repository;

import java.util.List;

import no.ntnu.idatt2106.model.DAO.ChatMessageDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import no.ntnu.idatt2106.model.DAO.ListingCategoryDAO;
import no.ntnu.idatt2106.model.DAO.ListingDAO;
@Repository
public interface ListingCategoryRepository extends JpaRepository<ListingCategoryDAO, Integer> {
    //Finds all listingCategories of one listing.
    List<ListingCategoryDAO> findAllFromListingCategoryDAOByListingID(ListingDAO listingID);
}
