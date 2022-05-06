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

    public CommunityListingDAO getByCommunityAndListing(CommunityDAO community, ListingDAO listing){
        return communityListingRepository.findByCommunityIDAndListingID(community, listing);
    }

    /**
     * Saves a communityListingDAO to the CommunityListing junction table
     * @param communityDAO the community object where the listing will be posted
     * @param listingDAO the listing object of the listing
     */
    public void saveCommunityListing(CommunityDAO communityDAO, ListingDAO listingDAO){
        CommunityListingDAO communityListingDAO = new CommunityListingDAO();
        communityListingDAO.setCommunity(communityDAO);
        communityListingDAO.setListing(listingDAO);
        communityListingRepository.save(communityListingDAO);
    }
    /**
     * Finds all the communityIDs of a listing. 
     * @param listingID of the listing
     * @return an array of the communityIDs of the communities that contain the listing
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
     *  A method to delete all ..?
     * @param listing
     */
    public void deleteAllWithListing(ListingDAO listing){
        List<CommunityListingDAO> communityListings = communityListingRepository.findAllFromCommunityListingDAOByListingID(listing);
        for (CommunityListingDAO dao:communityListings){
            communityListingRepository.delete(dao);
        }
    }

    /**
     *  A method to retrieve all listings within a community
     * @param communityDAO of the community the user wants to get listings from
     * @return a list of all listings in the community
     */
    public List<CommunityListingDAO> getAllCommunityListingForCommunity(CommunityDAO communityDAO) {
        return communityListingRepository.findAllByCommunityID(communityDAO);
    }

    /**
     * A method to delete a listing from a community
     * @param communityListingDAO the user wants deleted
     */
    public void deleteCommunityListing(CommunityListingDAO communityListingDAO) {
        communityListingRepository.delete(communityListingDAO);
    }
}
