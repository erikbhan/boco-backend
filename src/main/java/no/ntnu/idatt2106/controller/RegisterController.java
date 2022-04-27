package no.ntnu.idatt2106.controller;

import java.util.Base64;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.RegisterUserDTO;
import no.ntnu.idatt2106.service.UserService;
import no.ntnu.idatt2106.util.HashUtil;

/**
 * Controller class for handling user registration
 */
@RestController
@CrossOrigin
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * Endpoint for registering new users. Creates a new user from the given information and saves it to the DB.
     * @param regInfo information given in request
     * @throws StatusCodeException if e-mail is already in use in the DB.
     */
    @PostMapping("/register")
@ApiResponse(responseCode = "200", description = "User registered successfully")
    @ApiResponse(responseCode = "400", description = "Bad Request; e-mail in use")
    @Operation(summary = "Register a new user with the given information")
    public void registerNewUserAccount(@RequestBody RegisterUserDTO regInfo) throws StatusCodeException {
        if (userService.findUserByEmail(regInfo.getEmail()) != null) throw new StatusCodeException(HttpStatus.BAD_REQUEST, "E-mail in use");
        
        HashUtil hashUtil = new HashUtil();
        byte[] salt = hashUtil.getRandomSalt();
        byte[] hashedPassword = hashUtil.getHashedPassword(regInfo.getPassword(), salt);
        
        UserDAO newUser = new UserDAO();
        newUser.setSalt(Base64.getEncoder().encodeToString(salt));
        newUser.setHash(Base64.getEncoder().encodeToString(hashedPassword));
        newUser.setEmail(regInfo.getEmail());
        newUser.setFirstName(regInfo.getFirstName());
        newUser.setLastName(regInfo.getLastName());
        newUser.setAddress(regInfo.getAddress());

        userService.saveUser(newUser);
    }
}
