package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserCommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserCommunityRepository extends JpaRepository<UserCommunityDAO, Integer> {
    @Query(value = "SELECT COUNT (*) FROM user_community where community_id=?1 AND is_administrator = true", nativeQuery = true)
    int countAdmins (int community_id);

    @Query(value = "SELECT COUNT(*) FROM public.user_community WHERE user_id=?1 and community_id=?2", nativeQuery = true)
    int isUser ( int user_id, int community_id);

    @Query(value = "SELECT is_administrator FROM public.user_community WHERE user_id=?1 AND community_id=?2", nativeQuery = true)
    boolean isAdmin ( int user_id, int community_id );

    @Query(value = "SELECT community_id FROM public.user_community WHERE user_id=?1 AND is_administrator = true", nativeQuery = true)
    List<Integer> getAdminCommunityIDs (int userID);

    boolean existsByUserID(UserDAO user);
    boolean existsByCommunityID(CommunityDAO communityDAO);
    List<UserCommunityDAO> findAllByUserID(UserDAO user);

    List<UserCommunityDAO> findAllByCommunityID(CommunityDAO communityDAO);
}
