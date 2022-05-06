package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.middleware.RequireAuth;
import no.ntnu.idatt2106.model.DAO.*;
import no.ntnu.idatt2106.model.DTO.CommunityDTO;
import no.ntnu.idatt2106.model.DTO.ListingDTO;
import no.ntnu.idatt2106.model.DTO.TokenDTO;
import no.ntnu.idatt2106.model.DTO.UserDTO;
import no.ntnu.idatt2106.service.*;
import no.ntnu.idatt2106.util.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class CommunityController {
    private final CommunityService communityService;
    private final UserCommunityService userCommunityService;
    private final UserService userService;
    private final ListingService listingService;
    private final CommunityListingService communityListingService;

    public CommunityController(CommunityService communityService, UserCommunityService userCommunityService, UserService userService, ListingService listingService, CommunityListingService communityListingService) {
        this.communityService = communityService;
        this.userCommunityService = userCommunityService;
        this.userService = userService;
        this.listingService = listingService;
        this.communityListingService = communityListingService;
    }

    /**
     * Adds a community to the database
     * @param communityDTO community transfer object for community to add.
     */
    @RequireAuth
    @Operation(summary = "Add community to database")
    @PostMapping("/communities/create")
    public int addCommunity(@RequestBody CommunityDTO communityDTO){
        CommunityDAO communityDAO = communityService.turnCommunityDTOIntoCommunityDAO(communityDTO);
        TokenDTO userToken = TokenUtil.getDataJWT();
        int tokenUserId = userToken.getAccountId();
        communityService.addCommunity(communityDAO);
        userCommunityService.addUserAsAdminToCommunity(tokenUserId, communityDAO);
        return communityDAO.getCommunityID();
    }

    /**
     * A method that shows all communities
     * @return a list of all communities
     */
    @Operation(summary = "Show all communities")
    @GetMapping("/communities")
    public List<CommunityDTO> showAllCommunities(){
        List<CommunityDAO> listOfCommunityDAOs = communityService
                .findAll();
        if (listOfCommunityDAOs != null && listOfCommunityDAOs.size() > 0) {
            List<CommunityDTO> listOfCommunities = communityService
                    .convertListCommunityDAOToListCommunityDTO(listOfCommunityDAOs);
            return listOfCommunities;
        }
        return new ArrayList<>();
    }

    /**
     * A method which searches the community table in the database for communities with a name containing the search word.
     * @param search_word The letter or word to search for, may be the name of the community.
     * @return A list of communities matching the search word
     */
    @Operation(summary = "Show all communities with name containing the search word")
    @ApiResponse(responseCode = "400", description = "No communities found")
    @GetMapping("/communities/search")
    public List<CommunityDTO> showAllCommunitiesMatchingSearchTerm(@RequestParam(name = "search_word") String search_word) throws StatusCodeException {
        List<CommunityDAO> listOfCommunityDAOs = communityService
                .findAllCommunityDAOWithContainingAGivenName(search_word);
        if (listOfCommunityDAOs != null && listOfCommunityDAOs.size() > 0) {
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
    @RequireAuth
    @Operation(summary = "Deletes a community from the database")
    @ApiResponse(responseCode = "400", description = "Community not found")
    @ApiResponse(responseCode = "401", description = "User not admin or part of given community")
    @DeleteMapping("/communities/{communityId}/remove")
    public void removeCommunity(@PathVariable int communityId) throws StatusCodeException {
        TokenDTO userToken = TokenUtil.getDataJWT();
        int tokenUserId = userToken.getAccountId();
        CommunityDAO communityDAO = communityService.findCommunityDAOByCommunityID(communityId);
        if (communityDAO == null) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Community not found");
        }
        if (!userCommunityService.userIsInCommunity(tokenUserId, communityDAO)) {
            throw new StatusCodeException(HttpStatus.UNAUTHORIZED, "User not a part of this community");
        }
        List<UserCommunityDAO> users = userCommunityService.findAllMembersInACommunityByCommunity(communityDAO);
        if (!userCommunityService.userIsAdminInCommunity(tokenUserId, communityId)) {
            throw new StatusCodeException(HttpStatus.UNAUTHORIZED, "User not an admin in this community");
        }
        for (UserCommunityDAO user : users) {
            userCommunityService.removeUserFromCommunity(user);
        }
        List<CommunityListingDAO> listings = communityListingService.getAllCommunityListingForCommunity(communityDAO);
        for (CommunityListingDAO listing : listings) {
            communityListingService.deleteCommunityListing(listing);
        }
        communityService.removeCommunity(communityDAO);
    }

    /**
     * A method to get all members in a community.
     * @param communityId The id of the community to search for.
     * @return a list of all the members in a community
     */
    @Operation(summary = "Returns all members in a community")
    @ApiResponse(responseCode = "400", description = "No communities was found")
    @ApiResponse(responseCode = "417", description = "No members in members list")
    @GetMapping("/community/{communityId}/members")
    public List<UserDTO> getMembersInCommunity(@PathVariable int communityId) throws StatusCodeException {
        CommunityDAO communityDAO = communityService.findCommunityDAOByCommunityID(communityId);
        if (communityDAO == null) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Community not found");
        }
        List<UserCommunityDAO> userCommunityDAOs = userCommunityService
                .findAllMembersInACommunityByCommunity(communityDAO);
        if (userCommunityDAOs != null) {
            try {
                List<UserDAO> membersDAO = userCommunityService
                        .makeListOfAllMembersInACommunity(userCommunityDAOs);
                List<UserDTO> membersList = userService.convertListUserDAOToListUserDTO(membersDAO);
                if (membersList != null) {
                    return membersList;
                }
                throw new StatusCodeException(HttpStatus.EXPECTATION_FAILED, "Member list is empty");
            } catch (Exception e) {
                throw new StatusCodeException(HttpStatus.EXPECTATION_FAILED, "Member list is empty");
            }
        }
        return new ArrayList<>();
    }

    /**
     * A method for returning a community by community id.
     * @param communityId The id of the community
     * @return CommunityDTO with information about the community
     */
    @Operation(summary = "Returns a community with the correct id")
    @ApiResponse(responseCode = "400", description = "No community was found")
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
     * @return returns a community's listings
     */
    @Operation(summary = "Returns all the listings in a community")
    @ApiResponse(responseCode = "400", description = "No communities was found")
    @ApiResponse(responseCode = "417", description = "No listings in the community")
    @GetMapping("/community/{communityId}/listings")
    public List<ListingDTO> getAllListingsInACommunity(@PathVariable int communityId) throws StatusCodeException {
        CommunityDAO communityDAO = communityService.findCommunityDAOByCommunityID(communityId);
        if (communityDAO == null) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Community not found");
        }
        UserDAO user;
        if (communityDAO.getVisibility() == 0) {
            try {
                TokenDTO token = TokenUtil.getDataJWT();
                user = userService.findUserByUserId(token.getAccountId());

            } catch (Exception e) {
                e.printStackTrace();
                throw new StatusCodeException(HttpStatus.BAD_REQUEST, "not logged in");
            }

            if (user == null) {
                throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Could not find user/ not logged in ");
            }

            if (!userCommunityService.userIsInCommunity(user.getUserID(), communityDAO)) {
                throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Community is private and you're not in it");
            }

        }

        List<ListingDAO> listingDAOS = listingService.getAllListingsInACommunity(communityDAO);

        List<ListingDTO> listings = listingService
                .convertListOfListingDAOToListOfListingDTO(listingDAOS);

        if (listings == null) {
            throw new StatusCodeException(HttpStatus.EXPECTATION_FAILED, "No listings in the community");
        }
        return listings;
    }
}
