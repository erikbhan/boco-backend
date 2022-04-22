package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.ListingDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.ListingDTO;
import no.ntnu.idatt2106.repository.ListingRepository;
import no.ntnu.idatt2106.repository.UserRepository;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ListingService {
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    public ListingService(ListingRepository listingRepository, UserRepository userRepository) {
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
    }

    public void saveListing(ListingDTO listingDTO){
        ListingDAO listing = new ListingDAO();
        listing.setTitle(listingDTO.getTitle());
        listing.setDescription(listingDTO.getAddress());
        listing.setAddress(listingDTO.getAddress());
        listing.setPricePerDay(listingDTO.getPricePerDay());
        listing.setUserID(userRepository.getById(listingDTO.getUserID()));
        listingRepository.save(listing);
    }

    
}
