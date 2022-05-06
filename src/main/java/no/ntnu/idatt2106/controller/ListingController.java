package no.ntnu.idatt2106.controller;

import java.util.List;

import no.ntnu.idatt2106.model.DAO.*;
import no.ntnu.idatt2106.model.DTO.ListingPictureDTO;
import no.ntnu.idatt2106.model.DTO.ListingWithUnavailabilityDTO;
import no.ntnu.idatt2106.model.DTO.TokenDTO;
import no.ntnu.idatt2106.service.*;
import no.ntnu.idatt2106.util.TokenUtil;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.model.DTO.ListingDTO;

/**
 * The controller for handling api request related to Listings
 */
@RestController
@CrossOrigin
public class ListingController {
    private final ListingService listingService;
    private final ListingCategoryService listingCategoryService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final CommunityListingService communityListingService;
    private final CommunityService communityService;
    private final ListingPictureService listingPictureService;
    private final RentService rentService;

    public ListingController(ListingService listingService, ListingCategoryService listingCategoryService,
                             UserService userService, CategoryService categoryService,
                             CommunityListingService communityListingService, CommunityService communityService,
                             ListingPictureService listingPictureService, RentService rentService) {
        this.listingService = listingService;
        this.listingCategoryService = listingCategoryService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.communityListingService = communityListingService;
        this.communityService = communityService;
        this.listingPictureService = listingPictureService;
        this.rentService = rentService;
    }

    /**
     * Get all listings in the database
     * @return a list of all listings on BOCO
     */
    @Operation(summary = "Returning every single listing")
    @GetMapping("/listing")
    public List<ListingDTO> getAllListings() {
        List<ListingDAO> listingDAOs = listingService.getAllListings();
        List<ListingDTO> listingDTOs =
        listingService.convertMultipleFromListingDAOToDTO(listingDAOs);
        return listingDTOs;
    }

    /**
     * Finds all the active user's listings
     * @return a list of the user's active listings
     */
    @Operation(summary = "Returning every listing of the active user")
    @ApiResponse(responseCode = "400", description = "User doesn't exist")
    @GetMapping("/listing/userListings")
    public List<ListingDTO> getAllOfAUsersListings() throws StatusCodeException {
        TokenDTO userToken = TokenUtil.getDataJWT();
        Integer tokenUserId = Integer.valueOf(userToken.getAccountId());
        UserDAO user = userService.findUserByUserId(tokenUserId);
        if (user == null) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User doesn't exist");
        }
        List<ListingDAO> listingDAOs = listingService.getAllOfNonDeletedListings(user);
        List<ListingDTO> listingDTOs = 
        listingService.convertListOfListingDAOToListOfListingDTO(listingDAOs);
        return listingDTOs;
    }

    /**
     * Method for finding a specific listing by a listingID
     * @param listingID the listing id of the listing to be searched for
     * @return a listing object
     */
    @Operation(summary = "Gets the listing with the given listing id")
    @ApiResponse(responseCode = "400", description = "Item doesn't exist")
    @GetMapping("/listing/{listingID}")
    public ListingDTO getListingDAOByID(@PathVariable int listingID) throws StatusCodeException {
        ListingDAO listingDAO = listingService.getListingDAOByID(listingID);
        if (listingDAO == null) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Item doesn't exist");
        }
        return listingService.convertOneListingDAOToDTO(listingDAO);
    }

    /**
     * Method for posting a listing.
     * @param listingDTO The listing to be posted
     * @return returns true if the listing was posted succesfully, false if not
     */
    @Operation(summary = "Post Listing and adding all the listing's categories to the ListingCategory junction table")
    @ApiResponse(responseCode = "400", description = "User or category not found")
    @PostMapping("/listing")
    public boolean postListing(@RequestBody ListingDTO listingDTO) throws StatusCodeException {
        ListingDAO listing = new ListingDAO();
        listing.setTitle(listingDTO.getTitle());
        listing.setDescription(listingDTO.getDescription());
        listing.setAddress(listingDTO.getAddress());
        listing.setPricePerDay(listingDTO.getPricePerDay());
        listing.setDeleted(false);
        listing.setUser(userService.findUserByUserId(listingDTO.getUserID()));
        listing.setDeleted(false);
        if (listing.getUser() == null) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User not found");
        }
        listingService.saveListing(listing);
        try {
            for (String categoryName : listingDTO.getCategoryNames()) {
                if (categoryService.findCategoryDAOByName(categoryName) == null){
                    CategoryDAO cat = new CategoryDAO(categoryName);
                    categoryService.addCategory(cat);
                }
                listingCategoryService.saveListingCategory(categoryService.findCategoryDAOByName(categoryName),
                        listing);
            }
        }catch (Exception e){throw new StatusCodeException(HttpStatus.BAD_REQUEST, "could not find category");}
        for (int communityID : listingDTO.getCommunityIDs()) {
            communityListingService.saveCommunityListing(communityService.findCommunityDAOByCommunityID(communityID),
                    listing);
        }
        return true;
    }

    /**
     * A method for posting a listing with a given availability
     * @param listingDTO The listing to be posted, containing unavailable times
     */
    @Operation(summary = "Post Listing and adding all the listing's categories to the ListingCategory junction table")
    @ApiResponse(responseCode = "200", description = "Listing created, unavailable times added")
    @ApiResponse(responseCode = "400", description = "User not found")
    @PostMapping("/listing/dates")
    public void postListingWithDate(@RequestBody ListingWithUnavailabilityDTO listingDTO) throws StatusCodeException {
        ListingDAO listing = new ListingDAO();
        listing.setTitle(listingDTO.getTitle());
        listing.setDescription(listingDTO.getDescription());
        listing.setAddress(listingDTO.getAddress());
        listing.setDeleted(false);
        listing.setPricePerDay(listingDTO.getPricePerDay());
        listing.setUser(userService.findUserByUserId(listingDTO.getUserID()));
        listing.setDeleted(false);
        if (listing.getUser() == null) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User not found");
        }
        listingService.saveListing(listing);
        for (String categoryName : listingDTO.getCategoryNames()) {
            if (categoryService.findCategoryDAOByName(categoryName) == null){
                CategoryDAO cat = new CategoryDAO(categoryName);
                categoryService.addCategory(cat);
            }
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
     * Updates the given listing in the database
     * @param listingDTO How the updated listing should look
     * @return returns true if the update was successful
     */
    @Operation(summary = "Change or modify a listing")
    @ApiResponse(responseCode = "400", description = "User or category not found")
    @PutMapping("/listing/change")
    public boolean changeListing(@RequestBody ListingDTO listingDTO) throws StatusCodeException {
        ListingDAO listing = listingService.findListingByListingId(listingDTO.getListingID());
        listing.setTitle(listingDTO.getTitle());
        listing.setDescription(listingDTO.getDescription());
        listing.setAddress(listingDTO.getAddress());
        listing.setPricePerDay(listingDTO.getPricePerDay());
        listing.setUser(userService.findUserByUserId(listingDTO.getUserID()));
        if (listing.getUser() == null) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User not found");
        }
        listingService.saveListing(listing);
        try {
            for (String categoryName : listingDTO.getCategoryNames()) {
                if (categoryService.findCategoryDAOByName(categoryName) == null){
                    CategoryDAO cat = new CategoryDAO(categoryName);
                    categoryService.addCategory(cat);
                }
                listingCategoryService.saveListingCategory(categoryService.findCategoryDAOByName(categoryName),
                        listing);
            }
        }catch (Exception e){throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Could not find category");}
        for (int communityID : listingDTO.getCommunityIDs()) {
            communityListingService.saveCommunityListing(communityService.findCommunityDAOByCommunityID(communityID),
                    listing);
        }
        return true;
    }

    /**
     * Gets all the intervals where the listing with the given listingID
     * is unavailable.
     * @param listingID The listingId of the listing you want to check
     * @return a list of all the non available times
     */
    @ApiResponse(responseCode = "400", description = "Listing doesnt exist")
    @GetMapping("/listing/{listingID}/availability")
    @Operation(summary = "Returns a list representing availability of a listing")
    public List<List<Long>> getAvailabilityByListingID(@PathVariable int listingID) throws StatusCodeException {
        if (listingService.findListingByListingId(listingID) == null){
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Listing was not found");
        }
        rentService.deletePastRentRequests(listingID);
        return rentService.getNonAvailableTimes(listingID);
    }

    /**
     * Gets every listing with title containing requested phrase
     * @param title The phrase to search for
     * @return a list of the listings matching the search word
     */
    @Operation(summary = "Gets all listings with a title matching the input title")
    @GetMapping("/listing/title/{title}")
    public List<ListingDTO> searchForListingsByTitle(@PathVariable String title){
        return listingService.getListingDTOByTitle(title);
    }

    /**
     * A method for gettig all pictures for a listing from the DB.
     * @param listingid The id of the listing
     * @return a list of images for the listing
     */
    @Operation(summary = "Get all pictures for a listing")
    @ApiResponse(responseCode = "400", description = "pictureDAO list is null or listing id invalid")
    @GetMapping("/listing/{listingid}/pictures")
    public List<ListingPictureDTO> getAllPicturesForAListing(@PathVariable int listingid) throws StatusCodeException {
        if(listingid > 0) {
            List<ListingPictureDAO> pictureDAOs = listingPictureService.findAllPicturesWithListingId(listingid);
            if(pictureDAOs != null) {
                List<ListingPictureDTO> listOfPictures = listingPictureService
                        .convertListOfListingPictureDAOToListOfListingPictureDTO(pictureDAOs);
                if(listOfPictures != null) {
                    return listOfPictures;
                }
                throw new StatusCodeException(HttpStatus.BAD_REQUEST, "An exception occurred");
            }
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "PictureDAOs list is null");
        }
        throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Listing id must be larger than 0");
    }

    /**
     * Set listing variable "deleted" to true to hide them from user. Doesn't actually delete listing to dont create problems with ratings
     * @param listingId The ID of the listing to be set to deleted
     */
    @Operation(summary = "Set boolean deleted in Listing to true")
    @ApiResponse(responseCode = "401", description = "Unexpected error")
    @DeleteMapping("/listing/{listingId}")
    public void setListingToDeleted(@PathVariable int listingId) throws StatusCodeException{
        try{
        ListingDAO listing = listingService.findListingByListingId(listingId);
        listing.setDeleted(true);
        listingService.saveListing(listing);
        communityListingService.deleteAllWithListing(listing);
        }
        catch(Exception e){
            throw new StatusCodeException(HttpStatus.UNAUTHORIZED, "Unexpected error");
        }
    }
}
