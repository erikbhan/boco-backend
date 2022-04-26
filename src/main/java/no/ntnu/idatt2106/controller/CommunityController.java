package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.middleware.RequireAuth;
import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserCommunityDAO;
import no.ntnu.idatt2106.model.DTO.CommunityDTO;
import no.ntnu.idatt2106.model.DTO.TokenDTO;
import no.ntnu.idatt2106.model.ID.UserCommunityID;
import no.ntnu.idatt2106.service.CommunityService;
import no.ntnu.idatt2106.service.UserCommunityService;
import no.ntnu.idatt2106.util.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequireAuth
public class CommunityController {
    private final CommunityService communityService;
    private final UserCommunityService userCommunityService;

    public CommunityController(CommunityService communityService, UserCommunityService userCommunityService) {
        this.communityService = communityService;
        this.userCommunityService = userCommunityService;
    }

    /**
     * Adds community to database
     * @param communityDTO community transfer object for community to add.
     */
    @Operation(summary = "Add community to database")
    @PostMapping("/community/save")
    @ApiResponse(responseCode = "201", description = "Community created")
    public void addCommunity(@RequestBody CommunityDTO communityDTO) throws StatusCodeException {
        CommunityDAO communityDAO = communityService.turnCommunityDTOIntoCommunityDAO(communityDTO);
        TokenDTO userToken = TokenUtil.getDataJWT();
        int tokenUserId = Integer.valueOf(userToken.getAccountId());
        communityService.addCommunity(communityDAO);
        userCommunityService.addUserToCommunity(tokenUserId, communityDAO);
        throw new StatusCodeException(HttpStatus.CREATED, "Community created");
    }

    /**
     * A method which shows all communities with visibility 1.
     * @return Returns a list of all communities with visibility 1.
     * @throws StatusCodeException
     */
    @Operation(summary = "Add community to database")
    @ApiResponse(responseCode = "200", description = "Returns a list of all visible communities")
    @ApiResponse(responseCode = "400", description = "No communities was found")
    @PostMapping("/community/all")
    public List<CommunityDTO> showAllCommunities() throws StatusCodeException {
        List<CommunityDAO> listOfCommunityDAOs = communityService
                .findAllCommunityDAOWithGivenVisibility(1);

        if(listOfCommunityDAOs != null && listOfCommunityDAOs.size() > 0) {
            List<CommunityDTO> listOfCommunities = communityService
                    .convertListCommunityDAOToListCommunityDTO(listOfCommunityDAOs);

            return listOfCommunities;
        }
        throw new StatusCodeException(HttpStatus.BAD_REQUEST, "No communities was found");
    }

    /**
     * Deletes a community from the database
     * @param communityId ID of the community to be deleted
     */
    @Operation(summary = "Deletes a community from the database")
    @PostMapping("/community/{communityId}/remove")
    public void removeCommunity(@PathVariable int communityId) throws StatusCodeException {
        TokenDTO userToken = TokenUtil.getDataJWT();
        int tokenUserId = Integer.valueOf(userToken.getAccountId());
        CommunityDAO communityDAO = communityService.findCommunityDAOByCommunityID(communityId);
        if (communityDAO == null) {
            throw new StatusCodeException(HttpStatus.NOT_FOUND, "Community not found");
        }
        UserCommunityDAO userCommunityDAO = userCommunityService.getByIds(tokenUserId, communityDAO);
        if (userCommunityDAO == null) {
            throw new StatusCodeException(HttpStatus.UNAUTHORIZED, "User not a part of this community");
        } else if (!userCommunityDAO.isAdministrator()) {
            throw new StatusCodeException(HttpStatus.UNAUTHORIZED, "User not an admin in this community");
        }
        communityService.removeCommunity(communityDAO);
    }
}
