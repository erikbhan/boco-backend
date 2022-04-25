package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.ListingDAO;
import no.ntnu.idatt2106.model.DAO.RentDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.RentDTO;
import no.ntnu.idatt2106.repository.RentRepository;
import no.ntnu.idatt2106.service.UserService;
import no.ntnu.idatt2106.service.ListingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentService {
    private final RentRepository rentRepository;
    private final UserService userService;
    private final ListingService listingService;

    public RentService(RentRepository rentRepository, UserService userService, ListingService listingService) {
        this.rentRepository = rentRepository;
        this.listingService = listingService;
        this.userService = userService;
    }

    public void acceptRent(RentDAO rentDAO) {
        rentDAO.setAccepted(true);
    }

    public void deleteRent(int rentId) {
        rentRepository.delete(rentRepository.getById(rentId));
    }

    public RentDAO findRentByRentId(int id){return rentRepository.getById(id);}

    /*public void saveRent(RentDTO rentDTO) {
        RentDAO rent = new RentDAO();
        rent.setFromTime(rentDTO.getFromTime());
        rent.setToTime(rentDTO.getToTime());
        rent.setAccepted(rentDTO.isAccepted());
        rent.setListingOwnerID(listingService.findListingByListingId(rentDTO.getListingOwnerId()));
        rent.setRenterID(userService.findUserByUserId(rentDTO.getRenterId()));
        rentRepository.save(rent);
    }*/
    public RentDAO getRentFromId(int rentId) {
        return rentRepository.getById(rentId);
    }

    public List<RentDAO> findRentByUserID(UserDAO user){
        return rentRepository.findRentDAOSByRenterID(user);
    }
}
