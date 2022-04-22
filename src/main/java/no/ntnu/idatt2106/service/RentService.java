package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.RentDAO;
import no.ntnu.idatt2106.repository.RentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentService {
    private final RentRepository rentRepository;

    public RentService(RentRepository rentRepository) {this.rentRepository = rentRepository;}

    public List<RentDAO> findAllRentDAOWithRenterIdAndStatus(int renterId, boolean isAccepted) {
        System.out.println(String.format("FINDING RENT HISTORY FOR USER %s WITH STATUS OF THE RENT REQUEST %s", renterId, isAccepted));
        return rentRepository.findAllByRenterIDAndIsAccepted(renterId,isAccepted);
    }
}
