package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.RentDAO;
import no.ntnu.idatt2106.model.DAO.ListingDAO;
import no.ntnu.idatt2106.model.DAO.NotificationDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.RentDTO;
import no.ntnu.idatt2106.repository.RentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is uses the access-point to the rent table in the DB.
 * This class uses the methods from {@link no.ntnu.idatt2106.repository.RentRepository RentRepository}
 */
@Service
public class RentService {
    private final RentRepository rentRepository;
    private final UserService userService;
    private final NotificationService notificationService;
    private final ListingService listingService;

    public RentService(RentRepository rentRepository, UserService userService,
                       NotificationService notificationService, ListingService listingService) {
        this.rentRepository = rentRepository;
        this.userService = userService;
        this.notificationService = notificationService;
        this.listingService = listingService;
    }

    /**
     * A method to find all rent daos for a user with a renter id and the status of the rent dao.
     * @param renterId The id of the renter
     * @param isAccepted the status of the request
     * @return Returns a list of rent daos containing all rent daos for this user with this status.
     */
    public List<RentDAO> findAllRentDAOWithRenterIdAndStatus(int renterId, boolean isAccepted) {
        UserDAO renter = userService.findUserByUserId(renterId);
        return rentRepository.findAllByRenterIDAndIsAccepted(renter,isAccepted);
    }

    /**
     * A method to find all rent daos for a renter with a given id.
     * This method returns all rent daos with this id, both accepted and not.
     * @param renterId The id of the renter to search for.
     * @return Returns a list containing all rent daos with this renter id.
     */
    public List<RentDAO> findAllRentDAOWithRenterId(int renterId) {
        UserDAO renter = userService.findUserByUserId(renterId);
        return rentRepository.findAllByRenterID(renter);
    }

    /**
     * A method to find all rentDAOs with an owner id.
     * Returns a list of all rent daos for this owner, or null if no items are listed with this owner id.
     * @param ownerId The id of the owner of the listed items.
     * @return Returns a list of all rent daos for this owner,
     * or null if no items are listed with this owner id.
     */
    public List<RentDAO> findAllRentDAOWithOwnerId(int ownerId) {
        UserDAO owner = userService.findUserByUserId(ownerId);
        List<ListingDAO> allListedObjects = listingService.findAllListingDAOByIdOfOwner(owner);
        if(allListedObjects != null) {
            List<RentDAO> listOfAllAgreements = new ArrayList<>();
            for(int i = 0; i < allListedObjects.size(); i++) {
                List<RentDAO> allAgreementsForThisItem = rentRepository
                        .findAllByListingOwnerID(allListedObjects.get(i));
                if(allAgreementsForThisItem != null) {
                    for(int l = 0; l < allAgreementsForThisItem.size(); l++) {
                        listOfAllAgreements.add(allAgreementsForThisItem.get(l));
                    }
                }
            }
            return listOfAllAgreements;
        }
        return null;
    }

    /**
     * A method to filter a list of Rent daos by a given status.
     * Returns a selected list of rent daos from the given list, all containing the correct status.
     * @param list The list of rent daos to sort
     * @param isAccepted The status of the rent daos you want. Is either true for accepted or false for not.
     * @return Returns a selected list of rent daos from the given list, all containing the correct status.
     */
    public List<RentDAO> filterAListOfRentDAOByStatusOfAgreement(List<RentDAO> list, boolean isAccepted) {
        List<RentDAO> listWithCorrectStatus = new ArrayList<>();
        for(int i = 0; i < list.size(); i++) {
            RentDAO rentAgreement = list.get(i);
            if(rentAgreement.getIsAccepted() == isAccepted) {
                listWithCorrectStatus.add(rentAgreement);
            }
        }
        return listWithCorrectStatus;
    }

    /**
     * A method for saving a user to the DB.
     * This method returns a string with the accept-message if the save was successful.
     * @param agreement the rent agreement to be saved in the DB.
     * @return Returns a string with the accept-message if the save was successful.
     */
    public String saveNewRentAgreementToDB(RentDAO agreement) {
        rentRepository.save(agreement);
        return "The agreement was saved in the DB";
    }

    /**
     * A method for converting rent dtos to rent daos.
     * This method will leave all null fields in the rent dto empty in the rent dao.
     * @param rentDTO The rent dto to be made into a rent dao.
     * @return Returns the new rent dao made from the rent dto.
     */
    public RentDAO convertFromRentDTOTORentDAO(RentDTO rentDTO) {
        Integer notificationId = rentDTO.getNotificationId();
        Integer renterId = rentDTO.getRenterId();
        Integer listingId = rentDTO.getListingId();
        Integer rentId = rentDTO.getRentId();
        NotificationDAO notification = null;
        UserDAO renter = null;
        ListingDAO listing = null;
        if(notificationId != null) {
            notification = notificationService.getNotificationFromNotificationId(rentDTO.getNotificationId());
        }
        if(renterId != null) {
            renter = userService.findUserByUserId(rentDTO.getRenterId());
        }
        if(listingId != null) {
            listing = listingService.findListingByListingId(rentDTO.getListingId());
        }
        if(rentId != null) {
            RentDAO rentDAO = new RentDAO(rentDTO.getFromTime(), rentDTO.getToTime(), rentDTO.getAccepted(), listing, renter, notification);
            rentDAO.setRentID(rentDTO.getRentId());
            return rentDAO;
        } else {
            return new RentDAO(rentDTO.getFromTime(), rentDTO.getToTime(), rentDTO.getAccepted(), listing, renter, notification);
        }
    }

    public RentDTO convertFromRentDAOToRentDTO(RentDAO rentDAO) {
        return new RentDTO(rentDAO.getRentID(), rentDAO.getFromTime(), rentDAO.getToTime(),
                rentDAO.getIsAccepted(), rentDAO.getListingOwnerID().getListingID(),
                rentDAO.getRenterID().getUserID(), rentDAO.getNotificationID().getNotificationID());
    }

    public List<RentDTO> convertListOfRentDAOToListOfRentDTO(List<RentDAO> list) {
        List<RentDTO> convertedList = new ArrayList<>();
        for(int i = 0; i < convertedList.size(); i++) {
            convertedList.add(convertFromRentDAOToRentDTO(list.get(i)));
        }
        return convertedList;
    }

    /**
     * A method for changing the accept-status of a rent agreement to accepted.
     * @param rentDAO The rent agreement, which status should be changed.
     */
    public void acceptRent(RentDAO rentDAO) {
        rentDAO.setAccepted(true);
        rentRepository.save(rentDAO);
    }

    /**
     * A method which deletes a rent agreement from the db.
     * @param rentId The id of the rent agreement to be deleted.
     */
    public void deleteRent(int rentId) {
        rentRepository.delete(rentRepository.findByRentID(rentId));
    }

    /**
     * A method which finds a single rent dao from the DB.
     * @param rentId The id of the rent dao to search for.
     * @return Returns a rent dao object with the given rentId.
     */
    public RentDAO getRentFromId(int rentId) {
        return rentRepository.findByRentID(rentId);
    }
}
