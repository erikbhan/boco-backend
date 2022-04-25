package no.ntnu.idatt2106.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import no.ntnu.idatt2106.model.DAO.ListingCategoryDAO;
@Repository
public interface ListingCategoryRepository extends JpaRepository<ListingCategoryDAO, Integer> {
    
}
