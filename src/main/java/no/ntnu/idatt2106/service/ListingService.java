package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.ListingDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.ListingDTO;
import no.ntnu.idatt2106.repository.ListingRepository;
import no.ntnu.idatt2106.repository.UserRepository;

import java.util.Optional;

import org.springframework.stereotype.Service;


import no.ntnu.idatt2106.repository.ListingRepository;
import org.springframework.stereotype.Service;
import no.ntnu.idatt2106.model.DAO.UserDAO;

import java.util.List;

/**
 * This class is uses the access-point to the listing table in the DB.
 * This class uses the methods from {@link no.ntnu.idatt2106.repository.ListingRepository ListingRepository}
 */
@Service
public class ListingService {
    private final ListingRepository listingRepository;
    
    public ListingService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }
    
    public void saveListing(ListingDTO listingDTO){
        ListingDAO listing = new ListingDAO();
        listing.setTitle(listingDTO.getTitle());
        listing.setDescription(listingDTO.getAddress());
        listing.setAddress(listingDTO.getAddress());
        listing.setPricePerDay(listingDTO.getPricePerDay());
        //listing.setUserID(userRepository.getById(listingDTO.getUserID()));
        listingRepository.save(listing);
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

}
