package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.middleware.RequireAuth;
import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.CommunityDTO;
import no.ntnu.idatt2106.model.DTO.TokenDTO;
import no.ntnu.idatt2106.repository.CommunityRepository;
import no.ntnu.idatt2106.service.UserCommunityService;
import no.ntnu.idatt2106.service.UserService;
import no.ntnu.idatt2106.util.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin
@ApiResponse(responseCode = "200")
@RequireAuth
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
    @PostMapping("/communities/{communityId}/join")
    @ApiResponse(responseCode = "200", description = "Added user to community")
    @ApiResponse(responseCode = "400", description = "Illegal operation")
    @ApiResponse(responseCode = "500", description = "Unexpected error")
    public void addUserToCommunity(@PathVariable int communityId) throws StatusCodeException {
        TokenDTO token = TokenUtil.getDataJWT();
        CommunityDAO communityDAO = communityRepository.findCommunityDAOByCommunityID(communityId);
        if (communityDAO == null) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Community does not exist");
        }
        if(communityDAO.getVisibility()==0){
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "This community is invite only");
        }
        if (userCommunityService.userIsInCommunity(token.getAccountId(),communityDAO)){
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User is already in this community");
        }
        if (!(userCommunityService.addUserToCommunity(token.getAccountId(), communityDAO))){
            throw new StatusCodeException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error");
        }

    }

    @Operation(summary = "Remove user from community")
    @PostMapping("/communities/{communityId}/leave")
    @ApiResponse(responseCode = "200", description = "Removed user from community")
    @ApiResponse(responseCode = "400", description = "Illegal operation")
    @ApiResponse(responseCode = "500", description = "Unexpected error")
    public void removeUserFromCommunity(@PathVariable int communityId) throws StatusCodeException{
        TokenDTO token = TokenUtil.getDataJWT();
        CommunityDAO communityDAO = communityRepository.findCommunityDAOByCommunityID(communityId);
        if (communityDAO == null) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Community does not exist");
        }


    }

    @Operation(summary = "Get all communities a specific user is part of")
    @ApiResponse(responseCode = "200", description = "Found communities")
    @ApiResponse(responseCode = "400", description = "Illegal operation")
    @GetMapping("/user/communities")
    public ArrayList<CommunityDTO> getCommunitiesForUser() throws StatusCodeException {
        TokenDTO token = TokenUtil.getDataJWT();
        UserDAO user = userService.findUserByUserId(token.getAccountId());
        if (user == null) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User does not exist");
        }
        ArrayList<CommunityDTO> communityDTOS = new ArrayList<>();

        return userCommunityService.getAllCommunitiesForUser(user);
    }

}
