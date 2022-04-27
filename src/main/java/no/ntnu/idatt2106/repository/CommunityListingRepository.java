package no.ntnu.idatt2106.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.CommunityListingDAO;
import no.ntnu.idatt2106.model.DAO.ListingDAO;

public interface CommunityListingRepository extends JpaRepository<CommunityListingDAO, Long> {
    List<CommunityListingDAO> findAllFromCommunityListingDAOByListingID(ListingDAO listingID);

    List<CommunityListingDAO> findAllByCommunityID(CommunityDAO communityDAO);
}
