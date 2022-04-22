package no.ntnu.idatt2106.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2106.model.DAO.RentDAO;

import java.util.List;

public interface RentRepository extends JpaRepository<RentDAO, Long> {
    List<RentDAO> findAllByRenterIDAndIsAccepted(int renterID, boolean isAccepted);
}
