package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.DAO.UserCommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCommunityRepository extends JpaRepository<UserCommunityDAO, Integer> {
    List<UserCommunityDAO> findAllByUserID(UserDAO user);
}
