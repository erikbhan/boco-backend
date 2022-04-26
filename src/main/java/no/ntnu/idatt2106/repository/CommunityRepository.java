package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.DTO.CommunityDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;

public interface CommunityRepository extends JpaRepository<CommunityDAO, Integer> {
    CommunityDAO findCommunityDAOByCommunityID(int id);
}
