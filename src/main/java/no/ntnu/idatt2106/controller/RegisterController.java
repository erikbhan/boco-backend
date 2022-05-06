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
     */
    @PostMapping("/register")
    @ApiResponse(responseCode = "400", description = "if any fields are not as expected")
    @Operation(summary = "Register a new user with the given information")
    public void registerNewUserAccount(@RequestBody RegisterUserDTO regInfo) throws StatusCodeException {
        if (regInfo.getEmail().length() < 5) throw new StatusCodeException(HttpStatus.BAD_REQUEST, "E-mail is too short");
        if (regInfo.getEmail() == null) throw new StatusCodeException(HttpStatus.BAD_REQUEST, "E-mail is null");
        if (regInfo.getFirstName() == null) throw new StatusCodeException(HttpStatus.BAD_REQUEST, "First name is null");
        if (regInfo.getFirstName().length() < 1) throw new StatusCodeException(HttpStatus.BAD_REQUEST, "First name is empty");
        if (regInfo.getLastName().length() < 1) throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Last name is empty");
        if (regInfo.getLastName() == null) throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Last name is null");
        if (regInfo.getAddress() == null) throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Address is null");
        if (regInfo.getAddress().length() < 1) throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Address is empty");
        if (regInfo.getPassword() == null) throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Password is null");
        if (regInfo.getPassword().length() < 8) throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Password is less than 8 characters");
        if (userService.findUserByEmail(regInfo.getEmail()) != null) throw new StatusCodeException(HttpStatus.BAD_REQUEST, "E-mail in use");
        
        HashUtil hashUtil = new HashUtil();
        byte[] salt = hashUtil.getRandomSalt();
        byte[] hashedPassword = hashUtil.getHashedPassword(regInfo.getPassword(), salt);
        
        UserDAO newUser = new UserDAO(
                regInfo.getEmail(),
                regInfo.getFirstName(),
                regInfo.getLastName(),
                regInfo.getAddress(),
                null,
                Base64.getEncoder().encodeToString(salt),
                Base64.getEncoder().encodeToString(hashedPassword));

        userService.saveUser(newUser);
    }
}
