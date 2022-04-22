package no.ntnu.idatt2106.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2106.model.DAO.ListingDAO;

public interface ListingRepository extends JpaRepository<ListingDAO, Long> {

}
