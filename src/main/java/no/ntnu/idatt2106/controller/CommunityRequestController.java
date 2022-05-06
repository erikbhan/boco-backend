package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.middleware.RequireAuth;
import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserCommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.CommunityRequestDTO;
import no.ntnu.idatt2106.model.DTO.TokenDTO;
import no.ntnu.idatt2106.model.DTO.UserDTO;
import no.ntnu.idatt2106.repository.CommunityRepository;
import no.ntnu.idatt2106.service.CommunityRequestService;
import no.ntnu.idatt2106.service.UserCommunityService;
import no.ntnu.idatt2106.service.UserService;
import no.ntnu.idatt2106.util.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequireAuth
@ApiResponse(responseCode = "401", description = "Unauthorized")
public class CommunityRequestController {
    private final CommunityRequestService communityRequestService;
    private final CommunityRepository communityRepository;
    private final UserCommunityService userCommunityService;
    private final UserService userService;

    public CommunityRequestController(CommunityRequestService communityRequestService, CommunityRepository communityRepository, UserCommunityService userCommunityService, UserService userService) {
        this.communityRequestService = communityRequestService;
        this.communityRepository = communityRepository;
        this.userCommunityService = userCommunityService;
        this.userService = userService;
    }

    /**
     * Sends a request to join a private community
     * @param communityId The id of the community you want to join
     * @param communityRequestDTO The requestDTO containing the join request information
     */
    @Operation(summary = "Sends request to join a community")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @PostMapping("/communities/{communityId}/private/join")
    public void joinPrivateCommunity(@PathVariable int communityId, @RequestBody CommunityRequestDTO communityRequestDTO) throws StatusCodeException {
        TokenDTO token = TokenUtil.getDataJWT();
        CommunityDAO communityDAO = communityRepository.findCommunityDAOByCommunityID(communityId);
        if (communityDAO == null) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Community does not exist");
        }
        if(communityDAO.getVisibility()==1){
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "This community is open");
        }
        if (userCommunityService.userIsInCommunity(token.getAccountId(),communityDAO)){
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User is already in this community");
        }
        communityRequestService.addNewRequest(communityDAO,userService.findUserByUserId(token.getAccountId()), communityRequestDTO.getMessage());
    }

    /**
     * Accepts a community join request
     * @param communityId The community to look for the join request in
     * @param userId The id of the user to accept
     */
    @Operation(summary = "Accepts a users request to join a community")
    @ApiResponse(responseCode = "400", description = "Active user is not admin")
    @PostMapping("/communities/{communityId}/requests")
    public void acceptCommunityRequest(@PathVariable int communityId, @RequestParam int userId) throws StatusCodeException {
        TokenDTO token = TokenUtil.getDataJWT();
        CommunityDAO communityDAO = communityRepository.findCommunityDAOByCommunityID(communityId);
        UserCommunityDAO ucdForAdmin = userCommunityService.getByIds(token.getAccountId(), communityDAO );
        boolean adminStatus = ucdForAdmin.isAdministrator();
        if (adminStatus){
            if(Integer.valueOf(communityRequestService.findRequest(userId, communityId)) != null){
                UserDAO userDAO = userService.findUserByUserId(userId);
                communityRequestService.acceptRequest(userDAO, communityDAO);
                communityRequestService.removeRequest(userId, communityId);
            }
        }
        else throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Needs admin status to perform this operation");
    }

    /**
     * Removes the active user's community join request for the community
     * with the given community id
     * @param communityId The id of the community to remove the request from
     */
    @Operation(summary = "Removes own request to join a community")
    @ApiResponse(responseCode = "400", description = "Given community does not exist")
    @PatchMapping("/communities/{communityId}/requests/remove")
    public void removeCommunityRequest(@PathVariable int communityId) throws StatusCodeException {
        TokenDTO token = TokenUtil.getDataJWT();
        CommunityDAO communityDAO = communityRepository.findCommunityDAOByCommunityID(communityId);
        if(communityDAO==null){
            throw new StatusCodeException(HttpStatus.BAD_REQUEST,"Community does not exist" );
        }
        communityRequestService.removeRequest(token.getAccountId(), communityId);
    }

    /**
     * Removes the given user's community join request for the community
     * with the given community id
     * @param communityId The community id of the community to remove from
     * @param userId The user id of the owner of the request we want to remove
     */
    @Operation(summary = "Removes a users request to join a community")
    @ApiResponse(responseCode = "400", description = "Given community does not exist")
    @PatchMapping("/communities/{communityId}/requests/reject")
    public void rejectCommunityRequest(@PathVariable int communityId, @RequestParam int userId) throws StatusCodeException {
        TokenDTO token = TokenUtil.getDataJWT();
        CommunityDAO communityDAO = communityRepository.findCommunityDAOByCommunityID(communityId);
        if(communityDAO==null){
            throw new StatusCodeException(HttpStatus.BAD_REQUEST,"Community does not exist" );
        }
        UserCommunityDAO ucd = userCommunityService.getByIds(token.getAccountId(), communityDAO );
        boolean adminStatus = ucd.isAdministrator();
        if (adminStatus){
            communityRequestService.removeRequest(userId, communityId);
        }
    }

    /**
     * Gets all users that have requested to join the given community
     * @param communityId The community to check for requests in
     * @return a list of all the users requesting to join a community
     */
    @Operation(summary = "Gets all requests in a community")
    @ApiResponse(responseCode = "400", description = "Active user is not admin")
    @GetMapping("/communities/{communityId}/requests")
    public List<UserDTO> getRequests(@PathVariable int communityId) throws StatusCodeException {
        TokenDTO token = TokenUtil.getDataJWT();
        CommunityDAO communityDAO = communityRepository.findCommunityDAOByCommunityID(communityId);
        UserCommunityDAO ucd = userCommunityService.getByIds(token.getAccountId(), communityDAO );
        boolean adminStatus = ucd.isAdministrator();
        if(ucd!=null && adminStatus){
            return communityRequestService.getRequestsForCommunity(communityId);
        }
       else throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Needs admin status to see requests");
    }
}
