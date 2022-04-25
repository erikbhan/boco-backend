package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.RentDAO;
import no.ntnu.idatt2106.repository.RentRepository;
import org.springframework.stereotype.Service;

@Service
public class RentService {
    private final RentRepository rentRepository;

    public RentService(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }

    public void acceptRent(RentDAO rentDAO) {
        rentDAO.setAccepted(true);
    }

    public void deleteRent(int rentId) {
        rentRepository.delete(rentRepository.getById(rentId));
    }

    public RentDAO getRentFromId(int rentId) {
        return rentRepository.getById(rentId);
    }
}
