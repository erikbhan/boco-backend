package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserCommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCommunityRepository extends JpaRepository<UserCommunityDAO, Integer> {
    boolean existsByCommunityIDAndUserID(CommunityDAO communityDAO, UserDAO userDAO);
}
