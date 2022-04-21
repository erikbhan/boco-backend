package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.UserDTO;
import no.ntnu.idatt2106.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@CrossOrigin
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/findUser")
    public ResponseEntity getAUserFromUserId(@RequestBody UserDTO userDTO) {
        //Gotta add authentication when it is finished.

        System.out.println("Trying to find a user");
        UserDAO user = userService.findUserByUserId(Integer.valueOf(userDTO.getUserId()));
        if(user != null) {
            return ResponseEntity.ok().body(user);
        }
        return new ResponseEntity("No user was found", HttpStatus.NOT_FOUND);
    }
}
