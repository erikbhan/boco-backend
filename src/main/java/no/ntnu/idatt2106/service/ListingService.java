package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.CommunityListingDAO;
import no.ntnu.idatt2106.model.DAO.ListingDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.CommunityDTO;
import no.ntnu.idatt2106.model.DTO.ListingDTO;
import no.ntnu.idatt2106.repository.ListingRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;

/**
 * This class is uses the access-point to the listing table in the DB.
 * This class uses the methods from {@link no.ntnu.idatt2106.repository.ListingRepository ListingRepository}
 */
@Service
public class ListingService {
    private final ListingRepository listingRepository;
    private final CommunityListingService communityListingService;
    
    public ListingService(ListingRepository listingRepository,
                          CommunityListingService communityListingService) {
        this.listingRepository = listingRepository;
        this.communityListingService = communityListingService;
    }
    
    /**
     * Saves a ListingDAO to the DB
     * 
     * @param listingDAO
     */
    public void saveListing(ListingDAO listingDAO) {
        listingRepository.save(listingDAO);
    }

    /**
     * Finds all Listings in the Listing table
     * 
     * @return All listings
     */
    public List<ListingDAO> getAllListings() {
        return listingRepository.findAll();
    }

    /**
     * Finds all listing posted by a user
     * 
     * @param userDAO
     * @return All the user's listings
     */
    public List<ListingDAO> getAllOfUsersListings(UserDAO userDAO) {
        return listingRepository.findAllFromListingDAOByUserID(userDAO);
    }

    /**
     * Finds a specific listing
     * 
     * @param listingID
     * @return An optional with a specific listing
     */
    public Optional<ListingDAO> getListingDAOByID(int listingID) {
        return listingRepository.findById(listingID);
    }

    /**
     * Converts a list of ListingDAOs to ListingDTOs using the
     * convertOneListingDAOToDTO for every Object
     * 
     * @param listingCategoryService
     * @param communityListingService
     * @param listingDAOs             The list of DAOs that is to be converted
     * @return A list of converted DTOs.
     */
    public List<ListingDTO> convertMultipleFromListingDAOToDTO(ListingCategoryService listingCategoryService,
            CommunityListingService communityListingService, List<ListingDAO> listingDAOs) {
        List<ListingDTO> listingDTOs = new ArrayList<>();
        for (ListingDAO listingDAO : listingDAOs) {
            listingDTOs.add(convertOneListingDAOToDTO(listingCategoryService, communityListingService, listingDAO));
        }
        return listingDTOs;
    }

    /**
     * Method for converting a ListingDAO to a ListingDTO.
     * 
     * @param listingCategoryService
     * @param communityListingService
     * @param listingDAO              The ListingDAO that is to be converted
     * @return The converted ListingDAO, now a DTO
     */
    public ListingDTO convertOneListingDAOToDTO(ListingCategoryService listingCategoryService,
            CommunityListingService communityListingService, ListingDAO listingDAO) {
        // Finds all the listing's categorynames through the listingCategory junction
        // table
        String[] categoryNames = listingCategoryService.getCategoryNamesByListingID(listingDAO);
        // Finds all the listing's communityIDs through the communityListing junction
        // table
        int[] communityIDs = communityListingService.getCommunityListingIDsByListingID(listingDAO);
        // Creates a ListingDTO with the DAO-information, aswell as the categorynames
        // and communityIDs.
        ListingDTO listingDTO = new ListingDTO(listingDAO.getTitle(), listingDAO.getDescription(),
                listingDAO.getPricePerDay(), listingDAO.getAddress(), listingDAO.getUserID().getUserID(), categoryNames,
                communityIDs);
        return listingDTO;
    }

    /**
     * A method for finding a single listing instance with a given listing id.
     * @param listingId The id of the listing to search for.
     * @return Returns a listing dao with id matching the given id.
     */
    public ListingDAO findListingByListingId(int listingId) {
        return listingRepository.findListingDAOByListingID(listingId);
    }

    /**
     * A method for finding all listing instances for a given user DAO.
     * Returns a list of all listing daos in the db with this owner.
     * @param ownerId The UserDAO for the owner of the items.
     * @return Returns a list of all listing daos in the db with this owner dao.
     */
    public List<ListingDAO> findAllListingDAOByIdOfOwner(UserDAO ownerId) {
        return listingRepository.findAllByUserID(ownerId);
    }

    public List<ListingDAO> findListingsByUserDAO(UserDAO user) {
        return listingRepository.findListingDAOSByUserID(user);
    }

    /**
     * Returns a list of ListingDTOs with title containing requested phrase. 
     * @param title
     * @param listingCategoryService
     * @param communityListingService
     * @return
     */
    public List<ListingDTO> getListingDTOByTitle(String title, ListingCategoryService listingCategoryService,
    CommunityListingService communityListingService){
        //Gets all lisitngDAOs with the requested title
        List<ListingDAO> listingDAOs = listingRepository.findAllByTitleLike(title);
        //Converts all the DAOs to DTOs
        List<ListingDTO> listingDTOs = 
        convertMultipleFromListingDAOToDTO(listingCategoryService, communityListingService, listingDAOs);
        return listingDTOs;

    public List<ListingDAO> getAllListingsInACommunity(CommunityDAO communityDAO) {
        List<ListingDAO> listings = new ArrayList<>();
        List<CommunityListingDAO> communityListings = communityListingService
                .getAllCommunityListingForCommunity(communityDAO);

        if(communityListings != null) {
            for(int i = 0; i < communityListings.size(); i++) {
                listings.add(communityListings.get(i).getListingID());
            }
        }
        return listings;
    }

    public List<ListingDTO> convertListOfListingDAOToListOfListingDTO(List<ListingDAO> list) {
        List<ListingDTO> convertedList = new ArrayList<>();
        for(int i = 0; i < list.size(); i++) {
            convertedList.add(new ListingDTO(list.get(i)));
        }
        return convertedList;
    }
}
