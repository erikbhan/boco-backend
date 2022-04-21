package no.ntnu.idatt2106.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2106.model.DAO.CategoryDAO;

public interface CategoryRepository extends JpaRepository<CategoryDAO, Integer> {
    
}
