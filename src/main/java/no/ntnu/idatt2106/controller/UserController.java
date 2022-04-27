package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.middleware.RequireAuth;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.UserDTO;
import no.ntnu.idatt2106.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * The main controller for the api requests related to the user.
 */
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
    @GetMapping("/users/{userid}/profile")
    @Operation(summary = "Get at user by the user id")
    @ApiResponse(responseCode = "404", description = "User not found in the DB")
    public UserDTO getAUserFromUserId(@PathVariable() int userid) throws StatusCodeException {
        UserDAO user = userService.findUserByUserId(userid);
        if(user == null) {
            throw new StatusCodeException(HttpStatus.NOT_FOUND, "User not found in DB");
        }
        return new UserDTO(user);
    }
}
