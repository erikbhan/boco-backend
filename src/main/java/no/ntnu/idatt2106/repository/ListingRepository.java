package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.DAO.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2106.model.DAO.ListingDAO;

import java.util.List;

public interface ListingRepository extends JpaRepository<ListingDAO, Integer> {
    List<ListingDAO> findListingDAOSByUserID(UserDAO user);
}
