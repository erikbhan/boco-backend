package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.DAO.UserCommunityDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupRepository extends JpaRepository<UserCommunityDAO, Long> {
}
