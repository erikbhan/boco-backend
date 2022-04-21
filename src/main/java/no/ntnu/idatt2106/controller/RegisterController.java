package no.ntnu.idatt2106.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.RegisterUserDTO;

/**
 * Controller class for handling user registration
 */
@RestController
@CrossOrigin
public class RegisterController {
    
    @PostMapping("/register")
    public ResponseEntity<?> registerNewUserAccount(@RequestBody RegisterUserDTO regInfo) {
        UserDAO user = new UserDAO();
        user.setAddress(regInfo.getAddress());
        user.setEmail(regInfo.getEmail());
        user.setFirstName(regInfo.getFirstName());
        user.setPassword(regInfo.getPassword());
        user.setLastName(regInfo.getLastName());
        return new ResponseEntity<>("error", HttpStatus.FORBIDDEN);
    }
}
