package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.middleware.RequireAuth;
import no.ntnu.idatt2106.model.DAO.RatingDAO;
import no.ntnu.idatt2106.model.DTO.RatingDTO;
import no.ntnu.idatt2106.model.DTO.TokenDTO;
import no.ntnu.idatt2106.service.RatingService;
import no.ntnu.idatt2106.service.RentService;
import no.ntnu.idatt2106.service.UserService;
import no.ntnu.idatt2106.util.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class RatingController {
    private final RatingService ratingService;
    private final RentService rentService;
    private final UserService userService;

    public RatingController(RatingService ratingService, RentService rentService, UserService userService) {
        this.ratingService = ratingService;
        this.rentService = rentService;
        this.userService = userService;
    }

    /**
     * A method for finding a ratings of a user as the renter of an item
     * @param userID The id of the user you want to find ratings of
     * @return Returns a list of ratingDTOs
     * @throws StatusCodeException if the given user is not found or has no ratings as renter
     */
    @Operation(summary = "Finds ratings for user as renter")
    @ApiResponse(responseCode = "200", description = "returns a list of rating dtos for the user")
    @ApiResponse(responseCode = "400", description = "User not found in DB")
    @GetMapping("/rating/{userID}/as_renter")
    public List<RatingDTO> getAsRenter(@PathVariable int userID) throws StatusCodeException {
        List<RatingDTO> ratings = ratingService.findRatingsAsRenterByUserID(userID);
        if (!ratings.isEmpty()){
            return ratings;
        } else throw new StatusCodeException(HttpStatus.OK, "User not found or had no ratings as renter");
    }

    /**
     * A method for finding a ratings of a user as the owner of an item
     * @param userID The id of the user you want to find ratings of
     * @return Returns a list of ratingDTOs
     * @throws StatusCodeException if the given user is not found or has no ratings as owner
     */
    @Operation(summary = "Finds ratings for user as owner")
    @ApiResponse(responseCode = "200", description = "retuns a list of rating dtos for the owner")
    @ApiResponse(responseCode = "400", description = "User not found in DB")
    @GetMapping("/rating/{userID}/as_owner")
    public List<RatingDTO> getAsOwner(@PathVariable int userID) throws StatusCodeException {
        List<RatingDTO> ratings = ratingService.findRatingsAsOwnerByUserID(userID);
        if (!ratings.isEmpty()){
            return ratings;
        } else throw new StatusCodeException(HttpStatus.OK, "User not found or had no ratings as owner");
    }

    /**
     * A method for finding the average rating of a user with given userID
     * This takes into account both the ratings received as renter and as owner
     * @param userID The id of the user you want to find the average rating of
     * @return Returns a float of the average rating
     * @throws StatusCodeException if the given user is not found or has no ratings as renter
     */
    @Operation(summary = "Finds average rating of user")
    @ApiResponse(responseCode = "200", description = "returns the avrage rating of the user")
    @ApiResponse(responseCode = "400", description = "User not found in DB")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @GetMapping("/rating/{userID}/average")
    @RequireAuth
    public float getAverageRating(@PathVariable int userID) throws StatusCodeException {
        if (userService.findUserByUserId(userID) != null) {
            return ratingService.findAverageRating(userID);
        } else throw new StatusCodeException(HttpStatus.OK, "User not found or had no ratings as owner");
    }

    /**
     * A method that transforms a given ratingDTO into a ratingDAO, then adding
     * this ratingDAO to the database
     * @param ratingDTO The ratingDTO with the information to be added to the database
     * @return true if the given DTO was accepted and added to the database as a DAO
     *         false if a token was not received or if the owner of the token was not found
     */
    @Operation(summary = "Saves rating to db")
    @ApiResponse(responseCode = "200", description = "Returns true if the rating was posted and false if not")
    @ApiResponse(responseCode = "400", description = "User not found in DB")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @PostMapping("/rating/save")
    @RequireAuth
    public boolean postRating(@RequestBody RatingDTO ratingDTO){
        TokenDTO userToken;
        try {
            userToken = TokenUtil.getDataJWT();
        } catch (NullPointerException e) {
            return false;
        }
        int tokenUserID = userToken.getAccountId();
        if (userService.findUserByUserId(tokenUserID) != null){
            RatingDAO dao = new RatingDAO();
            dao.setComment(ratingDTO.getComment());
            dao.setScore(ratingDTO.getScore());
            dao.setRenterIsReceiverOfRating(ratingDTO.isRenterReceiverOfRating());
            dao.setRentID(rentService.getRentFromId(ratingDTO.getRentID()));
            ratingService.saveRating(dao);
            return true;
        }
        return false;
    }
}
