package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.LoginDTO;
import no.ntnu.idatt2106.service.LoginService;
import no.ntnu.idatt2106.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RequestMapping("/api")
@RestController
@CrossOrigin
public class LoginController {
    private final UserService userService;
    private final LoginService loginService;

    public LoginController(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
    }

    @PostMapping("/login/authentication")
    public ResponseEntity<String> loggingIn(@RequestBody LoginDTO loginDTO)
            throws NoSuchAlgorithmException, ServletException, IOException{
        System.out.println(loginDTO.email);
        System.out.println(loginDTO.password);
        if (loginService.attemptAuthentication(loginDTO.getEmail(), loginDTO.getPassword())) {
            UserDAO user = userService.findUserByEmail(loginDTO.getEmail());
            String token = loginService.successfulAuthentication(user);
            return new ResponseEntity(token, HttpStatus.OK);
        }
        return new ResponseEntity("Failed login", HttpStatus.BAD_REQUEST);
    }
}