package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.middleware.RequireAuth;
import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserCommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.CommunityDTO;
import no.ntnu.idatt2106.model.DTO.TokenDTO;
import no.ntnu.idatt2106.repository.CommunityRepository;
import no.ntnu.idatt2106.service.CommunityService;
import no.ntnu.idatt2106.service.UserCommunityService;
import no.ntnu.idatt2106.service.UserService;
import no.ntnu.idatt2106.util.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@ApiResponse(responseCode = "401", description = "Unauthorized")
@RequireAuth
public class UserCommunityController {
    private final UserCommunityService userCommunityService;
    private final UserService userService;
    private final CommunityRepository communityRepository;
    private final CommunityService communityService;

    public UserCommunityController(UserCommunityService userCommunityService, UserService userService, CommunityRepository communityRepository, CommunityService communityService) {
        this.userCommunityService = userCommunityService;
        this.userService = userService;
        this.communityRepository = communityRepository;
        this.communityService = communityService;
    }

    /**
     * Method to join a community
     * @param communityId the communityID of the community the user wants to join
     * @throws StatusCodeException BadRequest When the community is private, the community does not exist or if the user is already in the community
     */
    @Operation(summary = "Join an open community")
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
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "This community is private");
        }
        if (userCommunityService.userIsInCommunity(token.getAccountId(),communityDAO)){
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User is already in this community");
        }
        if (!(userCommunityService.addUserToCommunity(token.getAccountId(), communityDAO))){
            throw new StatusCodeException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error");
        }

    }

    /**
     * Method to check if user is in a given community
     * @param communityId the community id of the community the user wants to check if they're part of
     * @return true if the user is in the community, false if not
     */
    @Operation(summary = "Get info about if the user is in community")
    @GetMapping("/communities/{communityId}/user/status")
    @ApiResponse(responseCode = "200")
    public boolean checkIfUserIsInCommunity(@PathVariable int communityId){
        TokenDTO token = TokenUtil.getDataJWT();
        CommunityDAO communityDAO = communityRepository.findCommunityDAOByCommunityID(communityId);
        return userCommunityService.userIsInCommunity(token.getAccountId(),communityDAO);
    }

    /**
     * Checks if the user is admin in the community
     * @param communityId the community id of the community id you want to check if you are admin in
     * @return the admin status of the user
     */
    @Operation(summary = "Get info about if the user is admin in community")
    @GetMapping("/communities/{communityId}/user/admin")
    @ApiResponse(responseCode = "200")
    public boolean checkIfUserIsAdmin(@PathVariable int communityId){
        TokenDTO token = TokenUtil.getDataJWT();
        CommunityDAO communityDAO = communityRepository.findCommunityDAOByCommunityID(communityId);
        UserCommunityDAO ucd = userCommunityService.getByIds(token.getAccountId(), communityDAO );
        return ucd.isAdministrator();
    }

    /**
     * Method to get all the communities a user is admin in
     * @return a list of the communities (ids) the logged in user is admin of
     */
    @Operation(summary = "Gets the IDs of the communities the user is admin in")
    @GetMapping("/communities/admin")
    @ApiResponse(responseCode = "200")
    public List<Integer> getAdminCommunities(){
        TokenDTO userToken = TokenUtil.getDataJWT();
        Integer tokenUserId = Integer.valueOf(userToken.getAccountId());
        return userCommunityService.getIdOfAllAdminCommunities(tokenUserId);
    }

    /**
     * Method to leave a community
     * @param communityId of the community you want to leave
     * @throws StatusCodeException
     */
    @Operation(summary = "Leaves a community")
    @PatchMapping("/communities/{communityId}/leave")
    @ApiResponse(responseCode = "200", description = "Removed user from community")
    @ApiResponse(responseCode = "400", description = "Illegal operation")
    @ApiResponse(responseCode = "500", description = "Unexpected error")
    public void leaveCommunity(@PathVariable int communityId) throws StatusCodeException{
        TokenDTO token = TokenUtil.getDataJWT();
        CommunityDAO communityDAO = communityRepository.findCommunityDAOByCommunityID(communityId);
        UserCommunityDAO ucd = userCommunityService.getByIds(token.getAccountId(), communityDAO );

        //Prevents admin from leaving group if there are no other admins, removes community if admin is the only one in the community
        if(ucd != null){
        if(ucd.isAdministrator() && (userCommunityService.findAllMembersInACommunityByCommunity(communityDAO).size()>=2)){
            if(userCommunityService.getAdminsSize(communityId)<=1){
                throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Can not leave community, needs a new admin");
            }
        }
        else if (ucd.isAdministrator()){
            if(!communityService.removeCommunity(communityDAO)){
                throw new StatusCodeException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error");
            }
        }
        }
        if (communityDAO == null) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Community does not exist");
        }
        if (!(userCommunityService.userIsInCommunity(token.getAccountId(),communityDAO))){
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User is not in this community");
        }
        assert ucd != null;
        if(!(userCommunityService.removeUserFromCommunity(ucd))){
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Unexpected error");
        }
    }

    /**
     * Method for an admin to kick a user from a community
     * @param communityId the communityID of the community you want to kick the user from
     * @param userId of the user you want kicked
     */
    @Operation(summary = "Kicks a user from a community")
    @ApiResponse(responseCode = "200", description = "Kicked user")
    @ApiResponse(responseCode = "400", description = "Illegal operation")
    @PatchMapping("/communities/{communityId}/kick")
    public void kickUserFromCommunity(@PathVariable int communityId, @RequestParam int userId) throws StatusCodeException {
        TokenDTO token = TokenUtil.getDataJWT();
        CommunityDAO communityDAO = communityRepository.findCommunityDAOByCommunityID(communityId);
        UserCommunityDAO ucd = userCommunityService.getByIds(token.getAccountId(), communityDAO );
        UserCommunityDAO userToBeKicked = userCommunityService.getByIds(userId, communityDAO);
        if(ucd!=null && userToBeKicked != null){
            if(ucd.isAdministrator() && (!userToBeKicked.isAdministrator())){
                userCommunityService.removeUserFromCommunity(userToBeKicked);
            }
            else {
                throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Need admin status to kick user from community / can not kick admin");
            }
        }
        else {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Something went wrong");
        }
    }

    /**
     * A method to get all communities the current user is part of
     * @return a list of the communities the user is part of
     */
    @Operation(summary = "Get all communities the logged in user is part of")
    @ApiResponse(responseCode = "200", description = "Found communities")
    @ApiResponse(responseCode = "400", description = "Illegal operation")
    @GetMapping("/user/communities")
    public ArrayList<CommunityDTO> getCommunitiesForUser(){
        TokenDTO token = TokenUtil.getDataJWT();
        UserDAO user = userService.findUserByUserId(token.getAccountId());
        return userCommunityService.getAllCommunitiesForUser(user);
    }
}
