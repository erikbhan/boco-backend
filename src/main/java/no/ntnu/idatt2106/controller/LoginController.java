package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.LoginDTO;
import no.ntnu.idatt2106.service.LoginService;
import no.ntnu.idatt2106.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@CrossOrigin
public class LoginController {
    private final UserService userService;
    private final LoginService loginService;

    public LoginController(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
    }

    /**
     * A method for logging in and authorizing a user
     * @param loginDTO A DTO containing email and password for the requested login
     * @return  A String with the user's token
     */
    @Operation(summary = "Log in the user")
    @ApiResponse(responseCode = "400", description = "Login failed")
    @ApiResponse(responseCode = "500", description = "Unexpected server error")
    @PostMapping("/login/authentication")
    public String login(@RequestBody LoginDTO loginDTO)
            throws StatusCodeException {
        try {
            if (!loginService.attemptAuthentication(loginDTO.getEmail(), loginDTO.getPassword()))
                throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Login failed");
            UserDAO user = userService.findUserByEmail(loginDTO.getEmail());
            return loginService.successfulAuthentication(user);
        } catch (NoSuchAlgorithmException | IOException | ServletException e) {
            e.printStackTrace();
            throw new StatusCodeException(HttpStatus.INTERNAL_SERVER_ERROR, "How did you get here");
    }}}
