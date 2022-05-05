package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.DAO.ImageDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageDAO, Integer> {
    ImageDAO findImageDAOByUserAndImage(UserDAO user, byte[] image);
}
