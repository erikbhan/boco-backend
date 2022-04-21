package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDAO findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
