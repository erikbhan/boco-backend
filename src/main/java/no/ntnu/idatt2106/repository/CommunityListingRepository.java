package no.ntnu.idatt2106.repository;

import java.util.List;

import no.ntnu.idatt2106.model.DAO.ChatMessageDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.CommunityListingDAO;
import no.ntnu.idatt2106.model.DAO.ListingDAO;
@Repository
public interface CommunityListingRepository extends JpaRepository<CommunityListingDAO, Long> {
    List<CommunityListingDAO> findAllFromCommunityListingDAOByListingID(ListingDAO listingID);
    CommunityListingDAO findByCommunityIDAndListingID(CommunityDAO community, ListingDAO listing);
    List<CommunityListingDAO> findAllByCommunityID(CommunityDAO communityDAO);
}
