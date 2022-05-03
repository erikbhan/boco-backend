package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.middleware.RequireAuth;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.TokenDTO;
import no.ntnu.idatt2106.model.DTO.UserDTO;
import no.ntnu.idatt2106.service.LoginService;
import no.ntnu.idatt2106.service.UserService;
import no.ntnu.idatt2106.util.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

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

    public UserController(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
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
    @PutMapping("/user/profile/password")
    @Operation(summary = "Change the password of the user with the given user token")
    @ApiResponse(responseCode = "200", description = "Returns a new token for the changed user")
    @ApiResponse(responseCode = "400", description = "User not found in the DB")
    public String changePasswordOfUser(@RequestBody String password) throws StatusCodeException, ServletException, IOException {
        TokenDTO userToken = TokenUtil.getDataJWT();
        Integer tokenUserId = Integer.valueOf(userToken.getAccountId());
        UserDAO userDAO = userService.findUserByUserId(tokenUserId);
        if(userDAO != null) {
            password = password.substring(13, password.length()-2);
            UserDAO changedUser = userService.changePasswordForUser(userDAO, password);
            return loginService.successfulAuthentication(changedUser);
        }
        throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User not found in DB");
    }

    @Operation(summary = "Checks to see if the password for the user is the correct password")
    @ApiResponse(responseCode = "200", description = "Password is a match")
    @ApiResponse(responseCode = "400", description = "The password provided did not match the password of the user")
    @ApiResponse(responseCode = "500", description = "Unexpected server error")
    @PostMapping("/login/authentication")
    public String checkPassword(@RequestBody String password) throws StatusCodeException {
        try {
            TokenDTO userToken = TokenUtil.getDataJWT();
            Integer tokenUserId = Integer.valueOf(userToken.getAccountId());
            UserDAO userDAO = userService.findUserByUserId(tokenUserId);
            if (!userService.attemptAuthenticationOfPassword(userDAO, password))
                throw new StatusCodeException(HttpStatus.BAD_REQUEST, "The password did not match");
            return "Password is a match";
        } catch (NoSuchAlgorithmException | StatusCodeException e) {
            e.printStackTrace();
            throw new StatusCodeException(HttpStatus.INTERNAL_SERVER_ERROR, "How did you get here");
        }
    }
}
