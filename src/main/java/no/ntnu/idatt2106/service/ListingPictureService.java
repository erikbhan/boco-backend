package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.model.DAO.ListingDAO;
import no.ntnu.idatt2106.model.DAO.ListingPictureDAO;
import no.ntnu.idatt2106.model.DTO.ListingPictureDTO;
import no.ntnu.idatt2106.repository.ListingPictureRepository;
import org.springframework.http.HttpStatus;
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

    /**
     * Finds all pictures containing a specific listing ID
     * @param listingId ID to find pictures for
     * @return A list of ListingPictureDAOs for the ID
     */
    public List<ListingPictureDAO> findAllPicturesWithListingId(int listingId) {
        ListingDAO listing = listingService.findListingByListingId(listingId);
        return listingPictureRepository.findAllByListing(listing);
    }

    /**
     * Change pictures for a listing
     * @param pictures list of pictures to change to
     * @param listing listing to change pictures for
     */
    public void changeListingsPictures(List<String> pictures, ListingDAO listing) {
        List<ListingPictureDAO> existing = listingPictureRepository.findAllByListing(listing);
        System.out.println("existing found...");
        List<ListingPictureDAO> newPictures = new ArrayList<>();
        for (String picture : pictures) {
            ListingPictureDAO dao = new ListingPictureDAO(picture, listing);
            newPictures.add(dao);
        }
        for (ListingPictureDAO picture : existing) {
            boolean inNewPics = false;
            for (ListingPictureDAO newPic : newPictures) {
                if (picture.equals(newPic)) {
                    inNewPics = true;
                    break;
                }
            }
            if (!inNewPics) {
                listingPictureRepository.delete(picture);
            }
        }
        for (ListingPictureDAO newPic : newPictures) {
            listingPictureRepository.save(newPic);
            System.out.println("added " + newPic.getPicture());
        }
    }

    /**
     * Convert a list of ListingPictureDAOs to a list of ListingPictureDTOs
     * @param list list of ListingPictureDAOs to convert
     * @return list of ListingPictureDTOs that has been converted
     */
    public List<ListingPictureDTO> convertListOfListingPictureDAOToListOfListingPictureDTO(List<ListingPictureDAO> list) {
        List<ListingPictureDTO> convertedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            convertedList.add(new ListingPictureDTO(list.get(i)));
        }
        return convertedList;
    }

    /**
     * Save a ListingPictureDAO
     * @param picture ListingPictureDAO to save
     */
    public void save(ListingPictureDAO picture) {
        listingPictureRepository.save(picture);
    }
}
