package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.RentDAO;
import no.ntnu.idatt2106.model.DTO.RentDTO;
import no.ntnu.idatt2106.repository.RentRepository;
import org.springframework.stereotype.Service;

@Service
public class RentService {
    private final RentRepository rentRepository;
    private final UserService userService;
    private final ListingService listingService;

    public RentService(RentRepository rentRepository, UserService userService, ListingService listingService) {
        this.rentRepository = rentRepository;
        this.userService = userService;
        this.listingService = listingService;
    }

    public void saveRent(RentDTO rentDTO) {
        RentDAO rent = new RentDAO();
        rent.setFromTime(rentDTO.getFromTime());
        rent.setToTime(rentDTO.getToTime());
        rent.setAccepted(rentDTO.isAccepted());
        rent.setListingOwnerID(listingService.getListingFromId(rentDTO.getListingOwnerId()));
        rent.setRenterID(userService.findUserByUserId(rentDTO.getRenterId()));
        rentRepository.save(rent);
    }
}
