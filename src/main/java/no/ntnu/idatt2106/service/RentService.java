package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.RentDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.repository.RentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class is uses the access-point to the rent table in the DB.
 * This class uses the methods from {@link no.ntnu.idatt2106.repository.RentRepository RentRepository}
 */
@Service
public class RentService {
    private final RentRepository rentRepository;
    private final UserService userService;

    public RentService(RentRepository rentRepository, UserService userService) {
        this.rentRepository = rentRepository;
        this.userService = userService;
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
}
