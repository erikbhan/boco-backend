package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.DAO.RentDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2106.model.DAO.RatingDAO;

import java.util.List;

public interface RatingRepository extends JpaRepository<RatingDAO, Integer> {
    RatingDAO findRatingDAOSByRatingID(int id);
    List<RatingDAO> findByRentAndRenterIsReceiverOfRatingTrue(RentDAO rentDAO);
    List<RatingDAO> findByRentAndRenterIsReceiverOfRatingFalse(RentDAO rentDAO);
    List<RatingDAO> findByRent(RentDAO rentDAO);
}
