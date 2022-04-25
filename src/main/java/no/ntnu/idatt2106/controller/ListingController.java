package no.ntnu.idatt2106.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.model.DAO.ListingDAO;
import no.ntnu.idatt2106.model.DTO.ListingDTO;
import no.ntnu.idatt2106.repository.ListingRepository;
import no.ntnu.idatt2106.repository.UserRepository;
import no.ntnu.idatt2106.service.CategoryService;
import no.ntnu.idatt2106.service.ListingCategoryService;
import no.ntnu.idatt2106.service.ListingService;
import no.ntnu.idatt2106.service.UserService;

/**
 * The controller for handling api request related to Listings
 */
@RestController
@CrossOrigin
@ApiResponse(responseCode = "200")
@ApiResponse(responseCode = "400")

public class ListingController {
    private final ListingService listingService;
    private final ListingCategoryService listingCategoryService;
    private final UserService userService;
    private final CategoryService categoryService;

    public ListingController(ListingService listingService, ListingCategoryService listingCategoryService,
            UserService userService,
            CategoryService categoryService) {
        this.listingService = listingService;
        this.listingCategoryService = listingCategoryService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    /**
     * The method to post a listing
     * @param listingDTO Object
     * @throws StatusCodeException
     */
    @PostMapping("/listing")
    @Operation(summary = "Post Listing and adding all the listing's categories to the ListingCategory junction table")

    public boolean postListing(@RequestBody ListingDTO listingDTO) throws StatusCodeException {
        try {
            //Creates a ListingDAO with the information from the DTO.
            ListingDAO listing = new ListingDAO();
            listing.setTitle(listingDTO.getTitle());
            listing.setDescription(listingDTO.getAddress());
            listing.setAddress(listingDTO.getAddress());
            listing.setPricePerDay(listingDTO.getPricePerDay());
            listing.setUserID(userService.findUserByUserId(listingDTO.getUserID()));
            //Saves the DAO to the DB
            listingService.saveListing(listing);
            //The for-loop goes through the categories of listing, adding them to the 
            //listingCategory table. 
            //Finds the categoryIDs from the category table using the categorynames. 
            for (String categoryName : listingDTO.getCategoryNames()) {
               System.out.println(listingCategoryService.saveListingCategory(categoryService.findCategoryDAOByName(categoryName),
                        listing));
                System.out.println("Hei Morten:)");
            }
            
            return true;
        } catch (Exception e) {
            throw new StatusCodeException(HttpStatus.INTERNAL_SERVER_ERROR, "Uff da");
        }

    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
