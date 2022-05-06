package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.CommunityListingDAO;
import no.ntnu.idatt2106.model.DAO.ListingDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.ListingDTO;
import no.ntnu.idatt2106.repository.ListingRepository;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * This class is uses the access-point to the listing table in the DB.
 * This class uses the methods from {@link no.ntnu.idatt2106.repository.ListingRepository ListingRepository}
 */
@Service
public class ListingService {
    private final ListingRepository listingRepository;
    private final CommunityListingService communityListingService;
    private final ListingCategoryService listingCategoryService;

    public ListingService(ListingRepository listingRepository, CommunityListingService communityListingService,
                          ListingCategoryService listingCategoryService) {
        this.listingRepository = listingRepository;
        this.communityListingService = communityListingService;
        this.listingCategoryService = listingCategoryService;
    }

    /**
     * Saves a ListingDAO to the DB
     * @param listingDAO
     */
    public void saveListing(ListingDAO listingDAO) {
        listingRepository.save(listingDAO);
    }

    /**
     * Finds all Listings in the Listing table
     * @return All listings
     */
    public List<ListingDAO> getAllListings() {
        return listingRepository.findAllByDeletedIsFalse();
    }

    /**
     * Finds all listing posted by a user
     * @param userDAO
     * @return All the user's listings
     */
    public List<ListingDAO> getAllOfUsersListings(UserDAO userDAO) {
        return listingRepository.findAllFromListingDAOByUser(userDAO);
    }

    /**
     * Retrieves all listings where variable deleted is false.
     * @param userDAO
     * @return
     */
    public List<ListingDAO> getAllOfNonDeletedListings(UserDAO userDAO) {
        //Finds all user listings
        List<ListingDAO> allUserListings = listingRepository.findAllFromListingDAOByUser(userDAO);
        List<ListingDAO> allNonDeletedUserListings = new ArrayList<>();
        //Finds all user listings where deleted is false
        for (ListingDAO listing : allUserListings) {
            if (!listing.isDeleted()) {
                allNonDeletedUserListings.add(listing);
            }
        }
        return allNonDeletedUserListings;
    }

    /**
     * Finds a specific listing
     * @param listingID
     * @return An optional with a specific listing
     */
    public ListingDAO getListingDAOByID(int listingID) {
        return listingRepository.findListingDAOByListingID(listingID);
    }

    /**
     * Converts a list of ListingDAOs to ListingDTOs using the
     * convertOneListingDAOToDTO for every Object
     * @param listingDAOs The list of DAOs that is to be converted
     * @return A list of converted DTOs.
     */
    public List<ListingDTO> convertMultipleFromListingDAOToDTO(List<ListingDAO> listingDAOs) {
        List<ListingDTO> listingDTOs = new ArrayList<>();
        for (ListingDAO listingDAO : listingDAOs) {
            listingDTOs.add(convertOneListingDAOToDTO(listingDAO));
        }
        return listingDTOs;
    }

    /**
     * Method for converting a ListingDAO to a ListingDTO.
     * @param listingDAO The ListingDAO that is to be converted
     * @return The converted ListingDAO, now a DTO
     */
    public ListingDTO convertOneListingDAOToDTO(ListingDAO listingDAO) {
        // Finds all the listing's categorynames through the listingCategory junction
        // table
        String[] categoryNames = listingCategoryService.getCategoryNamesByListingID(listingDAO);
        // Finds all the listing's communityIDs through the communityListing junction
        // table
        int[] communityIDs = communityListingService.getCommunityListingIDsByListingID(listingDAO);
        // Creates a ListingDTO with the DAO-information, aswell as the categorynames
        // and communityIDs.
        ListingDTO listingDTO = new ListingDTO(listingDAO.getTitle(), listingDAO.getDescription(),
                listingDAO.getPricePerDay(), listingDAO.getAddress(), listingDAO.getUser().getUserID(), categoryNames,
                communityIDs);
        listingDTO.setListingID(listingDAO.getListingID());
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
        return listingRepository.findAllByUserAndDeletedIsFalse(ownerId);
    }

    /**
     * Find all listings posted by a user
     * @param user user to find listings for
     * @return A list of all the users ListingDAOs
     */
    public List<ListingDAO> findListingsByUserDAO(UserDAO user) {
        return listingRepository.findListingDAOSByUser(user);
    }

    /**
     * Returns a list of ListingDTOs with title containing requested phrase.
     * @param title
     * @return
     */
    public List<ListingDTO> getListingDTOByTitle(String title) {
        //Gets all lisitngDAOs with the requested title
        List<ListingDAO> listingDAOs = listingRepository.findAllByTitleLike(title);
        //Converts all the DAOs to DTOs
        List<ListingDTO> listingDTOs =
                convertMultipleFromListingDAOToDTO(listingDAOs);
        return listingDTOs;
    }

    /**
     * Get all listings from a community
     * @param communityDAO Community to find listings for
     * @return List of ListingDAOs from the community
     */
    public List<ListingDAO> getAllListingsInACommunity(CommunityDAO communityDAO) {
        List<ListingDAO> listings = new ArrayList<>();
        List<CommunityListingDAO> communityListings = communityListingService
                .getAllCommunityListingForCommunity(communityDAO);

        if (communityListings != null) {
            for (int i = 0; i < communityListings.size(); i++) {
                listings.add(communityListings.get(i).getListing());
            }
        }
        return listings;
    }

    /**
     * Convert a list of ListingDAOs to a list of ListingDTOs
     * @param list List of ListingDAOs to convert
     * @return A list of ListingDTOs
     */
    public List<ListingDTO> convertListOfListingDAOToListOfListingDTO(List<ListingDAO> list) {
        List<ListingDTO> convertedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            convertedList.add(new ListingDTO(list.get(i)));
        }
        return convertedList;
    }

    /**
     * Gets the previous listing posted by a user
     * @param userID user to find listing for
     * @return ListingDAO that is the users last posted listing
     */
    public ListingDAO getUsersLastPostedListing(int userID) {
        return listingRepository.findLastAddedListingByUser(userID);
    }

    /**
     * Sets all listings made by a user to isDeleted = true
     * @param userDAO user to delete listings for
     */
    public void deleteListingsForUser(UserDAO userDAO) {
        List<ListingDAO> listings = getAllOfUsersListings(userDAO);
        for (ListingDAO listing : listings) {
            listing.setDeleted(true);
        }
    }
}
