package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.middleware.RequireAuth;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.TokenDTO;
import no.ntnu.idatt2106.model.DTO.UserDTO;
import no.ntnu.idatt2106.service.UserService;
import no.ntnu.idatt2106.util.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The main controller for the api requests related to the user.
 */
@RequestMapping("/api")
@RestController
@CrossOrigin
@ApiResponse(responseCode = "200")
@ApiResponse(responseCode = "401", description = "Not authenticated")
@RequireAuth
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * A method for finding a user from a user id.
     * @return Returns a response entity containing either the UserDAO object or
     * the http status not found if the user is not found within the DB.
     */
    @GetMapping("/user/findUser")
    @Operation(summary = "Get at user by the user id")
    @ApiResponse(responseCode = "404", description = "User not found in the DB")
    public UserDAO getAUserFromUserId() throws StatusCodeException {
        System.out.println("Trying to find a user");
        TokenDTO token = TokenUtil.getDataJWT();
        System.out.println("This is the token: " + token);
        System.out.println("The int is: " + Integer.valueOf(token.getAccountId()));
        UserDAO user = userService.findUserByUserId(Integer.valueOf(token.getAccountId()));
        if(user == null) {
            throw new StatusCodeException(HttpStatus.NOT_FOUND, "User not found in DB");
        }
        return user;
    }
}
