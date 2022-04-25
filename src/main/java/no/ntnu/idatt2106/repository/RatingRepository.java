package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.DAO.RentDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2106.model.DAO.RatingDAO;
import no.ntnu.idatt2106.model.DTO.RatingDTO;

import java.util.List;

public interface RatingRepository extends JpaRepository<RatingDAO, Integer> {
    List<RatingDAO> findByRentIDAndRenterIsReceiverOfRatingTrue(RentDAO rentDAO);
    List<RatingDAO> findByRentIDAndRenterIsReceiverOfRatingFalse(RentDAO rentDAO);
}
