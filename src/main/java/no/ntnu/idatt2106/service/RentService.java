package no.ntnu.idatt2106.service;

import antlr.Token;
import no.ntnu.idatt2106.model.DAO.RentDAO;
import no.ntnu.idatt2106.model.DAO.ListingDAO;
import no.ntnu.idatt2106.model.DAO.NotificationDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.ListingWithUnavailabilityDTO;
import no.ntnu.idatt2106.model.DTO.RentDTO;
import no.ntnu.idatt2106.repository.RentRepository;
import no.ntnu.idatt2106.util.TokenUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
        return rentRepository.findAllByRenterAndIsAccepted(renter,isAccepted);
    }

    /**
     * A method to find all rent daos for a renter with a given id.
     * This method returns all rent daos with this id, both accepted and not.
     * @param renterId The id of the renter to search for.
     * @return Returns a list containing all rent daos with this renter id.
     */
    public List<RentDAO> findAllRentDAOWithRenterId(int renterId) {
        UserDAO renter = userService.findUserByUserId(renterId);
        return rentRepository.findAllByRenter(renter);
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
                        .findAllByListing(allListedObjects.get(i));
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
        RentDAO rentDAO = new RentDAO(rentDTO);
        Integer renterId = rentDTO.getRenterId();
        Integer listingId = rentDTO.getListingId();
        Integer rentId = rentDTO.getRentId();
        NotificationDAO notification;
        UserDAO renter;
        ListingDAO listing;
        if(renterId != null) {
            renter = userService.findUserByUserId(rentDTO.getRenterId());
            rentDAO.setRenter(renter);
        }
        if(listingId != null) {
            listing = listingService.findListingByListingId(rentDTO.getListingId());
            rentDAO.setListing(listing);
        }
        if(rentId != null) {
            rentDAO.setRentID(rentDTO.getRentId());
        }
        return rentDAO;
    }

    public List<RentDTO> convertListOfRentDAOToListOfRentDTO(List<RentDAO> list) {
        List<RentDTO> convertedList = new ArrayList<>();
        for(int i = 0; i < list.size(); i++) {
            convertedList.add(new RentDTO(list.get(i)));
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
     * A method which changes the status of a rent dao to be true on deleted.
     * This marks it as deleted.
     * @param rentId The id of the rent agreement to be deleted.
     */
    public void deleteRent(int rentId) {
        RentDAO rentDAO = rentRepository.findByRentID(rentId);
        rentDAO.setDeleted(true);
        rentRepository.save(rentDAO);
    }

    /**
     * A method which finds a single rent dao from the DB.
     * @param rentId The id of the rent dao to search for.
     * @return Returns a rent dao object with the given rentId.
     */
    public RentDAO getRentFromId(int rentId) {
        return rentRepository.findByRentID(rentId);
    }

    /**
     * A method for filtering lists of RentDAOs on deleted.
     * This method filters out all rent DAOs that have been deleted.
     * @param list The list you want to filter.
     * @return Returns a list of rent DAOs containing only non deleted renting agreements.
     */
    public List<RentDAO> filterListOfRentDAOOnDeleted(List<RentDAO> list) {
        List<RentDAO> noDeletedRentDAOs = new ArrayList<>();
        for(int i = 0; i < list.size(); i++) {
            if(!list.get(i).isDeleted()) {
                noDeletedRentDAOs.add(list.get(i));
            }
        }
        return noDeletedRentDAOs;
    }

    public List<RentDAO> findRentByRenterID(UserDAO user){
        return rentRepository.findRentDAOSByRenter(user);
    }

    /**
     * Converts a LocalDateTime objet into a long of the milliseconds counting  from midnight 1.1.1970
     * @param ldt LocalDateTime of the date and time you want to convert
     * @return a long of the converted LocalDateTime
     */
    public long fromLocalDateTimeToMillis(LocalDateTime ldt){
        return ldt.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli();
    }

    /**
     * Finds all the intervals where the listing with the given listingID
     * is unavailable.
     * @param listingID The listingId of the listing you want to check
     * @return A list containing lists of rent start times and their
     *         corresponding ending times
     */
    public List<List<Long>> getNonAvailableTimes(int listingID){
        List<RentDAO> rentDAOs= rentRepository.findRentDAOSByListing(listingService.getListingDAOByID(listingID));
        ArrayList<List<Long>> nonAvailableTimes = new ArrayList<>();
        for(RentDAO rentDAO:rentDAOs) {
            ArrayList<Long> addThis = new ArrayList<>();
            addThis.add(rentDAO.getFromTime());
            addThis.add(rentDAO.getToTime());
            nonAvailableTimes.add(addThis.subList(0,2));
        }
        return nonAvailableTimes.subList(0,nonAvailableTimes.size());
    }

    public List<RentDAO> turnListingWithUnavailabilityDTOIntoRentDAO(ListingWithUnavailabilityDTO dto) {
        ArrayList<RentDAO> unavailabilityRents = new ArrayList<>();
        for (List<Long> dates : dto.getUnavailabilityDates()){
            RentDAO rentDAO = new RentDAO();
            rentDAO.setFromTime(dates.get(0));
            rentDAO.setToTime(dates.get(1));
            rentDAO.setListing(listingService.findListingByListingId(dto.getListingID()));
            rentDAO.setRenter(userService.findUserByUserId(dto.getUserID()));
            rentDAO.setDeleted(false);
            rentDAO.setAccepted(true);
            unavailabilityRents.add(rentDAO);
        }
        return unavailabilityRents;
    }

    public RentDTO[] getAllRents() {
        // Get accountId
        UserDAO account = userService.findUserByUserId(TokenUtil.getDataJWT(TokenUtil.getToken()).getAccountId());
        List<RentDAO> rentDAOs = rentRepository.findAllByRenterIDOrListingOwnerID_UserID(account, account);
        RentDTO[] rentDTOs = new RentDTO[rentDAOs.size()];
        for (int i = 0; i < rentDAOs.size(); i++) {
            rentDTOs[i] = new RentDTO(rentDAOs.get(i));
        }
        return rentDTOs;
    }
}
