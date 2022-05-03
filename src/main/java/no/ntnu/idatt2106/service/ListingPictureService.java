package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.ListingDAO;
import no.ntnu.idatt2106.model.DAO.ListingPictureDAO;
import no.ntnu.idatt2106.model.DTO.ListingPictureDTO;
import no.ntnu.idatt2106.repository.ListingPictureRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListingPictureService {
    private final ListingPictureRepository listingPictureRepository;
    private final ListingService listingService;

    public ListingPictureService(ListingPictureRepository listingPictureRepository, ListingService listingService) {
        this.listingPictureRepository = listingPictureRepository;
        this.listingService = listingService;
    }

    public List<ListingPictureDAO> findAllPicturesWithListingId(int listingId) {
        ListingDAO listing = listingService.findListingByListingId(listingId);
        return listingPictureRepository.findAllByListing(listing);
    }

    public List<ListingPictureDTO> convertListOfListingPictureDAOToListOfListingPictureDTO(List<ListingPictureDAO> list) {
        List<ListingPictureDTO> convertedList = new ArrayList<>();
        for(int i = 0; i < list.size(); i++) {
            convertedList.add(new ListingPictureDTO(list.get(i)));
        }
        return convertedList;
    }
}
