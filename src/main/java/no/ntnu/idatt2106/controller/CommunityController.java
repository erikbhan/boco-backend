package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.middleware.RequireAuth;
import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.ListingDAO;
import no.ntnu.idatt2106.model.DAO.UserCommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.CommunityDTO;
import no.ntnu.idatt2106.model.DTO.ListingDTO;
import no.ntnu.idatt2106.model.DTO.TokenDTO;
import no.ntnu.idatt2106.model.DTO.UserDTO;
import no.ntnu.idatt2106.service.CommunityService;
import no.ntnu.idatt2106.service.ListingService;
import no.ntnu.idatt2106.service.UserCommunityService;
import no.ntnu.idatt2106.service.UserService;
import no.ntnu.idatt2106.util.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@ApiResponse(responseCode = "401", description = "Unauthorized")
public class CommunityController {
    private final CommunityService communityService;
    private final UserCommunityService userCommunityService;
    private final UserService userService;
    private final ListingService listingService;

    public CommunityController(CommunityService communityService,
                               UserCommunityService userCommunityService, UserService userService,
                               ListingService listingService) {
        this.communityService = communityService;
        this.userCommunityService = userCommunityService;
        this.userService = userService;
        this.listingService = listingService;
    }

    /**
     * Adds community to database
     * @param communityDTO community transfer object for community to add.
     */
    @RequireAuth
    @Operation(summary = "Add community to database")
    @PostMapping("/communities/create")
    @ApiResponse(responseCode = "201", description = "Community created")
    public void addCommunity(@RequestBody CommunityDTO communityDTO) throws StatusCodeException {
        CommunityDAO communityDAO = communityService.turnCommunityDTOIntoCommunityDAO(communityDTO);
        TokenDTO userToken = TokenUtil.getDataJWT();
        int tokenUserId = userToken.getAccountId();
        communityService.addCommunity(communityDAO);
        userCommunityService.addUserToCommunity(tokenUserId, communityDAO);
        throw new StatusCodeException(HttpStatus.CREATED, "Community created");
    }

    /**
     * A method which shows all communities with visibility 1.
     * @return Returns a list of all communities with visibility 1.
     * @throws StatusCodeException
     */
    @Operation(summary = "Show all visible communities")
    @ApiResponse(responseCode = "200", description = "Returns a list of all visible communities")
    @ApiResponse(responseCode = "400", description = "No communities was found")
    @GetMapping("/communities")
    public List<CommunityDTO> showAllCommunities() throws StatusCodeException {
        List<CommunityDAO> listOfCommunityDAOs = communityService
                .findAll();

        if(listOfCommunityDAOs != null && listOfCommunityDAOs.size() > 0) {
            List<CommunityDTO> listOfCommunities = communityService
                    .convertListCommunityDAOToListCommunityDTO(listOfCommunityDAOs);

            return listOfCommunities;
        }
        throw new StatusCodeException(HttpStatus.BAD_REQUEST, "No communities was found");
    }

    /**
     * A method which searches the community table in the DB for communities with a name containing the search word.
     * @param search_word The letter or word to search for, may be the name of the community.
     * @return Returns a list of all communities with a name containing this search word.
     * @throws StatusCodeException
     */
    @RequireAuth
    @Operation(summary = "Show all communities with name containing the search word")
    @ApiResponse(responseCode = "200", description = "Returns a list of all communities matching the search word")
    @ApiResponse(responseCode = "400", description = "No communities was found")
    @GetMapping("/communities/search")
    public List<CommunityDTO> showAllCommunitiesMatchingSearchTerm(@RequestParam(name = "search_word") String search_word) throws StatusCodeException {
        List<CommunityDAO> listOfCommunityDAOs = communityService
                .findAllCommunityDAOWithContainingAGivenName(search_word);

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
    @DeleteMapping("/communities/{communityId}/remove")
    @ApiResponse(responseCode = "404", description = "Community not found")
    @RequireAuth
    public void removeCommunity(@PathVariable int communityId) throws StatusCodeException {
        TokenDTO userToken = TokenUtil.getDataJWT();
        int tokenUserId = userToken.getAccountId();
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

    /**
     * A method to get all members in a community.
     * @param communityId The id of the community to search for.
     * @return Returns a list of all the members in the given community.
     * @throws StatusCodeException
     */
    @Operation(summary = "Returns all members in a community")
    @ApiResponse(responseCode = "200", description = "Returns a list of all members in the community")
    @ApiResponse(responseCode = "400", description = "No communities was found, or no users in given community")
    @ApiResponse(responseCode = "417", description = "No members in members list")
    @GetMapping("/community/{communityId}/members")
    @RequireAuth
    public List<UserDTO> getMembersInCommunity(@PathVariable int communityId) throws StatusCodeException {
        CommunityDAO communityDAO = communityService.findCommunityDAOByCommunityID(communityId);
        if (communityDAO == null) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Community not found");
        }
        List<UserCommunityDAO> userCommunityDAOs = userCommunityService
                .findAllMembersInACommunityByCommunity(communityDAO);

        if(userCommunityDAOs != null) {
            try {
                List<UserDAO> membersDAO = userCommunityService
                        .makeListOfAllMembersInACommunity(userCommunityDAOs);

                List<UserDTO> membersList = userService.convertListUserDAOToListUserDTO(membersDAO);
                if(membersList != null) {
                    return membersList;
                }
                throw new StatusCodeException(HttpStatus.EXPECTATION_FAILED, "Member list is empty");
            } catch (Exception e) {
                throw new StatusCodeException(HttpStatus.EXPECTATION_FAILED, "Member list is empty");
            }
        }
        throw new StatusCodeException(HttpStatus.BAD_REQUEST, "No users in this community");
    }

    /**
     * A method for returning a community by community id.
     * @param communityId The id of the community
     * @return Returns a community if it is found or an error message if not.
     * @throws StatusCodeException
     */
    @RequireAuth
    @Operation(summary = "Returns a community with the correct id")
    @ApiResponse(responseCode = "200", description = "Returns the community")
    @ApiResponse(responseCode = "400", description = "No communities was found")
    @GetMapping("/community/{communityId}")
    public CommunityDTO getCommunity(@PathVariable int communityId) throws StatusCodeException {
        CommunityDAO communityDAO = communityService.findCommunityDAOByCommunityID(communityId);
        if (communityDAO == null) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Community not found");
        }

        return new CommunityDTO(communityDAO);
    }

    /**
     * A method for getting all listings in a community.
     * @param communityId The id of the community
     * @return Returns a list of all listings in the community.
     * @throws StatusCodeException
     */
    @RequireAuth
    @Operation(summary = "Returns a community with the correct id")
    @ApiResponse(responseCode = "200", description = "Returns the community")
    @ApiResponse(responseCode = "400", description = "No communities was found")
    @ApiResponse(responseCode = "417", description = "No listings in the community")
    @GetMapping("/community/{communityId}/listings")
    public List<ListingDTO> getAllListingsInACommunity(@PathVariable int communityId) throws StatusCodeException {
        CommunityDAO communityDAO = communityService.findCommunityDAOByCommunityID(communityId);
        if (communityDAO == null) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Community not found");
        }
        List<ListingDAO> listingDAOS = listingService.getAllListingsInACommunity(communityDAO);

        List<ListingDTO> listings = listingService
                .convertListOfListingDAOToListOfListingDTO(listingDAOS);

        if(listings == null) {
            throw new StatusCodeException(HttpStatus.EXPECTATION_FAILED, "No listings in the community");
        }
        return listings;
    }
}
