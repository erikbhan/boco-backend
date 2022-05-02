package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.middleware.RequireAuth;
import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.CommunityRequestDAO;
import no.ntnu.idatt2106.model.DAO.UserCommunityDAO;
import no.ntnu.idatt2106.model.DTO.TokenDTO;
import no.ntnu.idatt2106.repository.CommunityRepository;
import no.ntnu.idatt2106.service.CommunityRequestService;
import no.ntnu.idatt2106.service.UserCommunityService;
import no.ntnu.idatt2106.service.UserService;
import no.ntnu.idatt2106.util.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Sends request to add user to a community")
    @PostMapping("/communities/{communityId}/private/join")
    @ApiResponse(responseCode = "200", description = "Sent request")
    @ApiResponse(responseCode = "400", description = "Illegal operation")
    @ApiResponse(responseCode = "500", description = "Unexpected error")
    public void joinPrivateCommunity(@PathVariable int communityId, @RequestBody String message) throws StatusCodeException {
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

        communityRequestService.addNewRequest(communityDAO,userService.findUserByUserId(token.getAccountId()), message);
    }

    //Add accept communityrequest
    @PostMapping("/communities/{communityId}/requests")
    public void acceptCommunityRequest(@PathVariable int communityId, @RequestBody int userId){
        TokenDTO token = TokenUtil.getDataJWT();
        CommunityDAO communityDAO = communityRepository.findCommunityDAOByCommunityID(communityId);
        UserCommunityDAO ucd = userCommunityService.getByIds(token.getAccountId(), communityDAO );
        boolean adminStatus = ucd.isAdministrator();
        if (adminStatus){

            //communityRequestService.accept();
            //check if user id is in community requests, then accept
        }
    }

    @Operation(summary = "Removes own request to join a community")
    @PatchMapping("/communities/{communityId}/requests/remove")
    @ApiResponse(responseCode = "200", description = "Removed request")
    @ApiResponse(responseCode = "400", description = "Illegal operation")
    public void removeCommunityRequest(@PathVariable int communityId) throws StatusCodeException {
        TokenDTO token = TokenUtil.getDataJWT();
        CommunityDAO communityDAO = communityRepository.findCommunityDAOByCommunityID(communityId);
        if(communityDAO==null){
            throw new StatusCodeException(HttpStatus.BAD_REQUEST,"Community does not exist" );
        }
        communityRequestService.removeRequest(token.getAccountId(), communityId);

    }

    @Operation(summary = "Removes a users request to join a community")
    @PatchMapping("/communitites/{communityId}/requests/reject")
    @ApiResponse(responseCode = "200", description = "Removed request")
    @ApiResponse(responseCode = "400", description = "Illegal operation")
    public void rejectCommunityRequest(@PathVariable int communityId, @RequestBody int userId) throws StatusCodeException {
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
    //add remove communityrequest


}
