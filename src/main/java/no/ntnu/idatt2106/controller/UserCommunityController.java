package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserCommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.UserCommunityDTO;
import no.ntnu.idatt2106.repository.CommunityRepository;
import no.ntnu.idatt2106.service.UserCommunityService;
import no.ntnu.idatt2106.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/addUserToCommunity")
    @ApiResponse(responseCode = "200", description = "Added user to community")
    @ApiResponse(responseCode = "500", description = "Unexpected server error")
    public void addUserToCommunity(@RequestBody UserCommunityDTO userCommunityDTO){
        UserDAO user =  userService.findUserByUserId(userCommunityDTO.getUserID());
        CommunityDAO communityDAO = communityRepository.findCommunityDAOByCommunityID(userCommunityDTO.getCommunityID());
        userCommunityService.addUserToCommunity(user, communityDAO);
    }





}
