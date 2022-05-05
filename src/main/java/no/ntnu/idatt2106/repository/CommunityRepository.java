package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.DTO.CommunityDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityRepository extends JpaRepository<CommunityDAO, Integer> {
    CommunityDAO findCommunityDAOByCommunityID(int id);

    List<CommunityDAO> findAllByVisibility(int visibilityStatus);

    //Remove the @Param and @Query when hibernate version is updated to 5.6.9 or more.
    @Query("SELECT m FROM CommunityDAO m WHERE m.name LIKE %:name%")
    List<CommunityDAO> findAllByNameLike(@Param("name") String name);


}
