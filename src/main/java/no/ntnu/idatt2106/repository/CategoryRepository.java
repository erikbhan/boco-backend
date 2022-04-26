package no.ntnu.idatt2106.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import no.ntnu.idatt2106.model.DAO.CategoryDAO;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryDAO, Integer> {
    CategoryDAO findCategoryDAOByName(String name);
}
