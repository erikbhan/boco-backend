package no.ntnu.idatt2106.controller;

import java.util.List;

import no.ntnu.idatt2106.model.DAO.RentDAO;
import no.ntnu.idatt2106.model.DTO.ListingWithUnavailabilityDTO;
import no.ntnu.idatt2106.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.middleware.RequireAuth;
import no.ntnu.idatt2106.model.DAO.ListingDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.ListingDTO;

/**
 * The controller for handling api request related to Listings
 */
@RestController
@CrossOrigin
@ApiResponse(responseCode = "401", description = "Unauthorized")
@RequireAuth
public class ListingController {
    private final ListingService listingService;
    private final ListingCategoryService listingCategoryService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final CommunityListingService communityListingService;
    private final CommunityService communityService;
    private final RentService rentService;

    public ListingController(ListingService listingService, ListingCategoryService listingCategoryService,
            UserService userService,
            CategoryService categoryService, CommunityListingService communityListingService,
            CommunityService communityService,
            RentService rentService) {
        this.listingService = listingService;
        this.listingCategoryService = listingCategoryService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.communityListingService = communityListingService;
        this.communityService = communityService;
        this.rentService = rentService;
    }

    /**
     * Got all of user listings
     * @return Returns every single listing in the DB
     */
    @ApiResponse(responseCode = "200", description = "All listings returned")
    @Operation(summary = "Returning every single listing")
    @GetMapping("/listing")
    public List<ListingDTO> getAllListings() {
        List<ListingDAO> listingDAOs = listingService.getAllListings();
        List<ListingDTO> listingDTOs = 
        listingService.convertMultipleFromListingDAOToDTO(listingCategoryService, communityListingService, listingDAOs);
        return listingDTOs;
    }

    /**
     * 
     * @param userID Owner of the listing
     * @return A list of all the user's listings
     * @throws StatusCodeException
     */
    @ApiResponse(responseCode = "200", description = "All of a user's listings")
    @ApiResponse(responseCode = "400", description = "User doesnt exist")
    @GetMapping("/listing/userListings/{userID}")
    public List<ListingDTO> getAllOfAUsersListings(@PathVariable int userID) throws StatusCodeException {
        UserDAO user = userService.findUserByUserId(userID);
        // Checks if the user exist
        if (user == null) {
            //Exception is thrown if the user does not exist
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User doesnt exist");
        }
        //Gets all the listing daos of the user
        List<ListingDAO> listingDAOs = listingService.getAllOfUsersListings(user);
        //Converts all the DAOs to DTO, to include categories and communities.
        List<ListingDTO> listingDTOs = 
        listingService.convertMultipleFromListingDAOToDTO(listingCategoryService, communityListingService, listingDAOs);
        return listingDTOs;
    }

    /**
     * Method for finding a specific listing by a listingID
     * 
     * @param listingID
     * @return A specific listing
     * @throws StatusCodeException
     */
    @ApiResponse(responseCode = "200", description = "Listing found")
    @ApiResponse(responseCode = "400", description = "Item doesnt exist")
    @GetMapping("/listing/{listingID}")
    public ListingDTO getListingDAOByID(@PathVariable int listingID) throws StatusCodeException {
        ListingDAO listingDAO = listingService.getListingDAOByID(listingID);
        //Checks if the listing exists
        if (listingDAO == null) {
            //If the listing does not exist an exception is thrown
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Item doesnt exist");
        }
        //If the listing exists it is converted to a DTO and returned
        return listingService.convertOneListingDAOToDTO(listingCategoryService, communityListingService, listingDAO);
    }

    /**
     * The method to post a listing.
     * 
     * @param listingDTO Object
     * @throws StatusCodeException
     */
    @ApiResponse(responseCode = "200", description = "Listing posted")
    @ApiResponse(responseCode = "400", description = "User not found")
    @PostMapping("/listing")
    @Operation(summary = "Post Listing and adding all the listing's categories to the ListingCategory junction table")
    public boolean postListing(@RequestBody ListingDTO listingDTO) throws StatusCodeException {
        // Creates a ListingDAO with the information from the DTO.
        ListingDAO listing = new ListingDAO();
        listing.setTitle(listingDTO.getTitle());
        listing.setDescription(listingDTO.getDescription());
        listing.setAddress(listingDTO.getAddress());
        listing.setPricePerDay(listingDTO.getPricePerDay());
        listing.setUser(userService.findUserByUserId(listingDTO.getUserID()));
        if (listing.getUser() == null) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User not found");
        }
        // Saves the DAO to the DB
        listingService.saveListing(listing);
        // The for-loop goes through the categories of listing, adding them to the
        // listingCategory table.
        try {
            for (String categoryName : listingDTO.getCategoryNames()) {
                listingCategoryService.saveListingCategory(categoryService.findCategoryDAOByName(categoryName),
                        listing);
            }
        }catch (Exception e){throw new StatusCodeException(HttpStatus.BAD_REQUEST, "could not find category");}
        // The for-loop goes through the communities of listing, adding them to the
        // communityListing table.
        // Finds communities using communityIDs
        for (int communityID : listingDTO.getCommunityIDs()) {
            communityListingService.saveCommunityListing(communityService.findCommunityDAOByCommunityID(communityID),
                    listing);
        }
        return true;
    }

    /**
     *
     * A method to post a listing with given availability
     * @param listingDTO
     * @throws StatusCodeException
     */
    @ApiResponse(responseCode = "200", description = "Listing created, unavailable times added")
    @ApiResponse(responseCode = "400", description = "User not found")
    @PostMapping("/listing/dates")
    @Operation(summary = "Post Listing and adding all the listing's categories to the ListingCategory junction table")
    public void postListingWithDate(@RequestBody ListingWithUnavailabilityDTO listingDTO) throws StatusCodeException {
        ListingDAO listing = new ListingDAO();
        listing.setTitle(listingDTO.getTitle());
        listing.setDescription(listingDTO.getDescription());
        listing.setAddress(listingDTO.getAddress());
        listing.setPricePerDay(listingDTO.getPricePerDay());
        listing.setUser(userService.findUserByUserId(listingDTO.getUserID()));
        if (listing.getUser() == null) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User not found");
        }
        listingService.saveListing(listing);
        for (String categoryName : listingDTO.getCategoryNames()) {
            listingCategoryService.saveListingCategory(categoryService.findCategoryDAOByName(categoryName),
                    listing);
        }
        for (int communityID : listingDTO.getCommunityIDs()) {
            communityListingService.saveCommunityListing(communityService.findCommunityDAOByCommunityID(communityID),
                        listing);
        }
        List<RentDAO> rents = rentService.turnListingWithUnavailabilityDTOIntoRentDAO(listingDTO);
        for (RentDAO rent : rents){
            rent.setListing(listing);
            rentService.saveNewRentAgreementToDB(rent);
        }
        throw new StatusCodeException(HttpStatus.OK, "listing created, unavailable times added");
    }

    /**
     * Gets all the intervals where the listing with the given listingID
     * is unavailable.
     * @param listingID The listingId of the listing you want to check
     * @return A list containing lists of rent start times and their
     *         corresponding ending times
     * @throws StatusCodeException when the given listingID doesn't match up with anything in the db
     */
    @ApiResponse(responseCode = "200", description = "Listing found")
    @ApiResponse(responseCode = "400", description = "Listing doesnt exist")
    @GetMapping("/listing/{listingID}/availability")
    @Operation(summary = "Returns a list representing availability of a listing")
    public List<List<Long>> getAvailabilityByListingID(@PathVariable int listingID) throws StatusCodeException {
        if (listingService.findListingByListingId(listingID) == null){
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Listing was not found");
        }
        return rentService.getNonAvailableTimes(listingID);
    }
    /**
     * Gets every listing with title containing requested phrase
     * @param title
     * @return List of DTOs containing the requested title in title 
     */
    @GetMapping("/listing/title/{title}")
    public List<ListingDTO> searchForListingsByTitle(@PathVariable String title){
        return listingService.getListingDTOByTitle(title, listingCategoryService, communityListingService);
    }
}
