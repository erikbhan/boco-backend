package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2106.model.DAO.CommunityRequestDAO;

import java.util.List;

public interface CommunityRequestRepository extends JpaRepository<CommunityRequestDAO, Integer> {
    List<CommunityRequestDAO> findCommunityRequestDAOSByCommunity(CommunityDAO community);
    CommunityRequestDAO findByCommunityRequestID(int communityRequestId);
}
