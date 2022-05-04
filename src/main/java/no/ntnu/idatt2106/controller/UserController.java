package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.middleware.RequireAuth;
import no.ntnu.idatt2106.model.DAO.ListingDAO;
import no.ntnu.idatt2106.model.DAO.UserCommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.CommunityDTO;
import no.ntnu.idatt2106.model.DTO.TokenDTO;
import no.ntnu.idatt2106.model.DTO.UserDTO;
import no.ntnu.idatt2106.service.*;
import no.ntnu.idatt2106.util.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * The main controller for the api requests related to the user.
 */
@RestController
@CrossOrigin
@ApiResponse(responseCode = "401", description = "Not authenticated")
@RequireAuth
public class UserController {
    private final UserService userService;
    private final LoginService loginService;
    private final ListingService listingService;
    private final UserCommunityService userCommunityService;
    private final RentService rentService;

    public UserController(UserService userService, LoginService loginService, ListingService listingService,
                          UserCommunityService userCommunityService, RentService rentService) {
        this.userService = userService;
        this.loginService = loginService;
        this.listingService = listingService;
        this.userCommunityService = userCommunityService;
        this.rentService = rentService;
    }

    /**
     * A method for finding a user from a user id.
     * @return Returns a response entity containing either the UserDAO object or
     * the http status not found if the user is not found within the DB.
     */
    @GetMapping("/users/{userid}/profile")
    @Operation(summary = "Get at user by the user id")
    @ApiResponse(responseCode = "200", description = "Returns a user with the given user id")
    @ApiResponse(responseCode = "404", description = "User not found in the DB")
    public UserDTO getAUserFromUserId(@PathVariable() int userid) throws StatusCodeException {
        UserDAO user = userService.findUserByUserId(userid);
        if(user == null) {
            throw new StatusCodeException(HttpStatus.NOT_FOUND, "User not found in DB");
        }
        return new UserDTO(user);
    }

    /**
     * A method for changing the password of a user.
     * @param password The password you want to change into.
     * @return Returns a new token for the changed user.
     * @throws StatusCodeException
     */
    @PutMapping("/user/password/change")
    @Operation(summary = "Change the password of the user with the given user token")
    @ApiResponse(responseCode = "200", description = "Returns a new token for the changed user")
    @ApiResponse(responseCode = "400", description = "User not found in the DB")
    public String changePasswordOfUser(@RequestBody String password) throws StatusCodeException, ServletException, IOException {
        try {
            TokenDTO userToken = TokenUtil.getDataJWT();
            Integer tokenUserId = Integer.valueOf(userToken.getAccountId());
            UserDAO userDAO = userService.findUserByUserId(tokenUserId);

            //Changes the password json string back into the two passwords
            String trimmedPassword = password.substring(28, password.length()-3);
            String[] twoPasswords = trimmedPassword.split("\",\"");
            String newPassword = twoPasswords[0];
            String oldPassword = twoPasswords[1].substring(14);

            if (!userService.attemptAuthenticationOfPassword(userDAO, oldPassword))
                throw new StatusCodeException(HttpStatus.BAD_REQUEST, "The password did not match");
            UserDAO changedUser = userService.changePasswordForUser(userDAO, newPassword);
            return loginService.successfulAuthentication(changedUser);
        } catch (NoSuchAlgorithmException | IOException | ServletException e) {
            e.printStackTrace();
            throw new StatusCodeException(HttpStatus.INTERNAL_SERVER_ERROR, "How did you get here");
        }
    }

    @PutMapping("/user/delete")
    public boolean deleteAccount() throws StatusCodeException {
        Integer tokenUserId;
        try {
            TokenDTO userToken = TokenUtil.getDataJWT();
            tokenUserId = userToken.getAccountId();
        } catch (Exception e) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Token not found");
        }

        UserDAO userDAO = userService.findUserByUserId(tokenUserId);
        List<ListingDAO> listings = listingService.getAllOfUsersListings(userDAO);
        for (ListingDAO listing:listings) {
            //TODO: listing.setIsDeleted(true);
        }

        List<CommunityDTO> communities = userCommunityService.getAllCommunitiesForUser(userDAO);

        for (CommunityDTO community : communities) {
            userCommunityService.deleteUserFromAllGroups(userDAO);
        }

        rentService.deleteAllRentsFromUser(userDAO);

        userDAO.setFirstName("Slettet");
        userDAO.setLastName("Konto: " + userDAO.getUserID());
        userDAO.setPicture("");
        userDAO.setHash("");
        userDAO.setSalt("");
        userDAO.setAddress("");
        userDAO.setEmail("");
    }
}
