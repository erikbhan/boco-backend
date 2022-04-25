package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.UserCommunityDTO;
import no.ntnu.idatt2106.repository.CommunityRepository;
import no.ntnu.idatt2106.service.UserCommunityService;
import no.ntnu.idatt2106.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@ApiResponse(responseCode = "200")
public class UserCommunityController {
    private final UserCommunityService userCommunityService;
    private final UserService userService;
    private final CommunityRepository communityRepository;

    public UserCommunityController(UserCommunityService userCommunityService, UserService userService, CommunityRepository communityRepository) {
        this.userCommunityService = userCommunityService;
        this.userService = userService;
        this.communityRepository = communityRepository;
    }

    @Operation(summary = "Add user to a community")
    @PostMapping("/addUserToCommunity")
    @ApiResponse(responseCode = "200", description = "Added user to community")
    @ApiResponse(responseCode = "400", description = "Illegal operation")
    public void addUserToCommunity(@RequestBody UserCommunityDTO userCommunityDTO) throws StatusCodeException {
        UserDAO user = userService.findUserByUserId(userCommunityDTO.getUserID());
        if (user == null) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User does not exist");
        }
        CommunityDAO communityDAO = communityRepository.findCommunityDAOByCommunityID(userCommunityDTO.getCommunityID());
        if (communityDAO == null) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Community does not exist");
        }
        if (userCommunityService.userIsInCommunity(user,communityDAO)){
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User is already in this community");
        }
        userCommunityService.addUserToCommunity(user, communityDAO);



    }

    @Operation(summary = "Get all communities a specific user is part of")
    @ApiResponse(responseCode = "200", description = "Found communities")
    @ApiResponse(responseCode = "400", description = "Illegal operation")
    @GetMapping("/getCommunitiesForUser")
    public void getCommunitiesForUser(@RequestBody UserCommunityDTO userCommunityDTO) throws StatusCodeException {
        UserDAO user = userService.findUserByUserId(userCommunityDTO.getUserID());
        if (user == null) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User does not exist");
        }
        userCommunityService.getAllCommunitiesForUser(user);
    }

}
