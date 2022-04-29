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
     * Saves a communityListingDAO to the CommunityListing junction table
     * @param communityDAO
     * @param listingDAO
     */
    public void saveCommunityListing(CommunityDAO communityDAO, ListingDAO listingDAO){
        CommunityListingDAO communityListingDAO = new CommunityListingDAO();
        communityListingDAO.setCommunityID(communityDAO);
        communityListingDAO.setListingID(listingDAO);
        communityListingRepository.save(communityListingDAO);
    }
    /**
     * Finds all the communityIDs of a listing. 
     * @param listingID
     * @return
     */
    public int[] getCommunityListingIDsByListingID(ListingDAO listingID){
        //Finds all communityListings of a listing
        List<CommunityListingDAO> communityDAOs = communityListingRepository.findAllFromCommunityListingDAOByListingID(listingID); 
        int[] communityIDs = new int[communityDAOs.size()];
        //Loops through to find the communityIDs
        for (int i = 0; i < communityIDs.length; i++) {
            communityIDs[i] = communityDAOs.get(i).getCommunityID().getCommunityID();
        }
        return communityIDs;
    }

    public List<CommunityListingDAO> getAllCommunityListingForCommunity(CommunityDAO communityDAO) {
        return communityListingRepository.findAllByCommunityID(communityDAO);
    }
}
