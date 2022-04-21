package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.UserDTO;
import no.ntnu.idatt2106.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The main controller for the api requests related to the user.
 */
@RestController
@CrossOrigin
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * A method for finding a user from a user id.
     * @param userDTO The object containing the token and user id.
     * @return Returns a response entity containing either the UserDAO object or
     * a http status not found if the user is not found within the DB.
     */
    @GetMapping("/user/findUser")
    public ResponseEntity getAUserFromUserId(@RequestBody UserDTO userDTO) {
        //Gotta add authentication when it is finished.
        //Should probably just be the token that is sent, does only use the user id.

        System.out.println("Trying to find a user");
        UserDAO user = userService.findUserByUserId(Integer.valueOf(userDTO.getUserId()));
        if(user != null) {
            return ResponseEntity.ok().body(user);
        }
        return new ResponseEntity("No user was found", HttpStatus.NOT_FOUND);
    }

    /**
     * A method to get the full name out of a user id.
     * @param userDTO The object containing the token and user id.
     * @return Returns a response entity containing either the full name of the user or
     * a http status not found if the user is not found in the DB.
     */
    @GetMapping("/user/fullname")
    public ResponseEntity getFullnameForAUser(@RequestBody UserDTO userDTO) {
        //Gotta add authentication when it is finished.
        //Should be changed to tokenDTO when its finished.

        System.out.println("Trying to find the full name of the user");
        String fullname = userService.findFullNameFromUserId(Integer.valueOf(userDTO.getUserId()));
        if(fullname != null || fullname.contentEquals(" ")) {
            return ResponseEntity.ok().body(fullname);
        }
        return new ResponseEntity("No user was found", HttpStatus.NOT_FOUND);
    }
}
