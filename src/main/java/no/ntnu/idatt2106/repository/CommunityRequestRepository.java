package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DTO.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2106.model.DAO.CommunityRequestDAO;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommunityRequestRepository extends JpaRepository<CommunityRequestDAO, Integer> {


    @Query(value = "SELECT DISTINCT community_request_id FROM public.community_request WHERE user_id=?1 and community_id=?2", nativeQuery = true)
    int findId (int user_id, int community_id);

    List<CommunityRequestDAO> findAllByCommunityID(CommunityDAO communityDAO);
}
