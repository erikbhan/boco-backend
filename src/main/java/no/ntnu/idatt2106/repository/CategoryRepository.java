package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.DAO.CategoryDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<CategoryDAO, Integer> {
    CategoryDAO findCategoryDAOByName(String name);
}
