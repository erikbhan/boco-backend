package no.ntnu.idatt2106.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.CommunityListingDAO;
import no.ntnu.idatt2106.model.DAO.ListingDAO;
import no.ntnu.idatt2106.repository.CommunityListingRepository;

@Service
public class CommunityListingService {
    @Autowired
    private final CommunityListingRepository communityListingRepository;

    public CommunityListingService(CommunityListingRepository communityListingRepository){
        this.communityListingRepository = communityListingRepository;
    }

    /**
     * Finds the CommunityListing that contains the given community and the given listing
     * @param community the community to search for
     * @param listing the listing to search for
     * @return The CommunityListing we were searching for
     */
    public CommunityListingDAO getByCommunityAndListing(CommunityDAO community, ListingDAO listing){
        return communityListingRepository.findByCommunityIDAndListingID(community, listing);
    }

    /**
     * Saves a communityListingDAO to the CommunityListing junction table
     * @param communityDAO The community of the new CommunityListing
     * @param listingDAO The listing of the new CommunityListing
     */
    public void saveCommunityListing(CommunityDAO communityDAO, ListingDAO listingDAO){
        CommunityListingDAO communityListingDAO = new CommunityListingDAO();
        communityListingDAO.setCommunity(communityDAO);
        communityListingDAO.setListing(listingDAO);
        communityListingRepository.save(communityListingDAO);
    }

    /**
     * Finds all the communityListing IDs that contains the given listing
     * @param listingID the listing to search for
     * @return An array of all the IDs
     */
    public int[] getCommunityListingIDsByListingID(ListingDAO listingID){
        //Finds all communityListings of a listing
        List<CommunityListingDAO> communityDAOs = communityListingRepository.findAllFromCommunityListingDAOByListingID(listingID); 
        int[] communityIDs = new int[communityDAOs.size()];
        //Loops through to find the communityIDs
        for (int i = 0; i < communityIDs.length; i++) {
            communityIDs[i] = communityDAOs.get(i).getCommunity().getCommunityID();
        }
        return communityIDs;
    }

    /**
     * Deletes all communityListing entries that contains the given listing
     * @param listing the listing to search for
     */
    public void deleteAllWithListing(ListingDAO listing){
        List<CommunityListingDAO> communityListings = communityListingRepository.findAllFromCommunityListingDAOByListingID(listing);
        for (CommunityListingDAO dao:communityListings){
            communityListingRepository.delete(dao);
        }
    }

    /**
     * Finds all communityListings that contains the given community
     * @param communityDAO the community to search for
     * @return A list of CommunityListings
     */
    public List<CommunityListingDAO> getAllCommunityListingForCommunity(CommunityDAO communityDAO) {
        return communityListingRepository.findAllByCommunityID(communityDAO);
    }

    /**
     * Deletes the given CommunityListing from the database
     * @param communityListingDAO the CommunityListing to delete
     */
    public void deleteCommunityListing(CommunityListingDAO communityListingDAO) {
        communityListingRepository.delete(communityListingDAO);
    }
}
