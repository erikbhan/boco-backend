package no.ntnu.idatt2106.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2106.model.DAO.ListingDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;

public interface ListingRepository extends JpaRepository<ListingDAO, Integer> {
    List<ListingDAO> findAllFromListingDAOByUserID(UserDAO userID);
}
