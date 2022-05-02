package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.DAO.ImageDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageDAO, Integer> {
}
