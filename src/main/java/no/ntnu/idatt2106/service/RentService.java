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
        System.out.println(String.format("FINDING RENT HISTORY FOR USER %s WITH STATUS OF THE RENT REQUEST %s", renterId, isAccepted));
        UserDAO renter = userService.findUserByUserId(renterId);
        return rentRepository.findAllByRenterIDAndIsAccepted(renter,isAccepted);
    }

    public List<RentDAO> findAllRentDAOWithRenterId(int renterId) {
        System.out.println(String.format("FINDING FULL RENT HISTORY FOR USER %s", renterId));
        UserDAO renter = userService.findUserByUserId(renterId);
        return rentRepository.findAllByRenterID(renter);
    }

    public List<RentDAO> findAllRentDAOWithOwnerId(int ownerId) {
        System.out.println(String.format("FINDING ALL LISTED OBJECTS FOR OWNER WITH ID %s", ownerId));
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

    public List<RentDAO> filterAListOfRentDAOByStatusOfAgreement(List<RentDAO> list,boolean isAccepted) {
        List<RentDAO> listWithCorrectStatus = new ArrayList<>();
        for(int i = 0; i < list.size(); i++) {
            RentDAO rentAgreement = list.get(i);
            if(rentAgreement.getIsAccepted() == isAccepted) {
                listWithCorrectStatus.add(rentAgreement);
            }
        }
        return listWithCorrectStatus;
    }

    public String saveNewRentAgreementToDB(RentDAO agreement) {
        rentRepository.save(agreement);
        return "The agreement was saved in the DB";
    }

    public RentDAO convertFromRentDTOTORentDAO(RentDTO rentDTO) {
        NotificationDAO notification = notificationService.getNotificationFromNotificationId(rentDTO.getNotificationId());
        UserDAO renter = userService.findUserByUserId(rentDTO.getRenterId());
        ListingDAO listing = listingService.findListingByListingId(rentDTO.getListingId());
        return new RentDAO(rentDTO.getFromTime(), rentDTO.getToTime(), rentDTO.getAccepted(), listing, renter, notification);
    }
}
