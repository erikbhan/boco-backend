package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2106.model.DAO.CommunityRequestDAO;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommunityRequestRepository extends JpaRepository<CommunityRequestDAO, Integer> {

    List<CommunityRequestDAO> findCommunityRequestDAOSByCommunity(CommunityDAO community);
    CommunityRequestDAO findByCommunityRequestID(int communityRequestId);
    @Query(value = "SELECT DISTINCT community_request_id FROM public.community_request WHERE user_id=?1 and community_id=?2", nativeQuery = true)
    int findId (int user_id, int community_id);
    List<CommunityRequestDAO> findAllByCommunity(CommunityDAO communityDAO);
    List<CommunityRequestDAO> findAllByUser(UserDAO userDAO);
}
