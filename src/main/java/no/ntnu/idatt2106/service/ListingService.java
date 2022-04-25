package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.ListingDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.repository.ListingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListingService {
    private final ListingRepository listingRepository;

    public ListingService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    public ListingDAO findListingByListingId(int listingId) {
        return listingRepository.findListingDAOByListingID(listingId);
    }

    public List<ListingDAO> findAllListingDAOByIdOfOwner(UserDAO ownerId) {
        return listingRepository.findAllByUserID(ownerId);
    }
}
