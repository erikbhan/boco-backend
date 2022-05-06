package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.middleware.RequireAuth;
import no.ntnu.idatt2106.model.DAO.RatingDAO;
import no.ntnu.idatt2106.model.DAO.RentDAO;
import no.ntnu.idatt2106.model.DTO.RatingDTO;
import no.ntnu.idatt2106.model.DTO.TokenDTO;
import no.ntnu.idatt2106.service.RatingService;
import no.ntnu.idatt2106.service.RentService;
import no.ntnu.idatt2106.service.UserService;
import no.ntnu.idatt2106.util.TokenUtil;
import org.springframework.dao.DataIntegrityViolationException;
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
     * A method for finding ratings of a user as the renter of an item
     * @param userID The id of the user you want to find ratings of
     * @return A list of ratings for the given user
     */
    @Operation(summary = "Finds ratings for user as renter")
    @ApiResponse(responseCode = "400", description = "User not found in database")
    @GetMapping("/rating/{userID}/renter")
    public List<RatingDTO> getAsRenter(@PathVariable int userID) throws StatusCodeException {
        List<RatingDTO> ratings = ratingService.findRatingsAsRenterByUserID(userID);
        if (userService.findUserByUserId(userID) == null){
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User not found in database");
        }
        return ratings;
    }

    /**
     * A method for finding ratings of a user as the owner of an item
     * @param userID The id of the user you want to find ratings of
     * @return A list of ratings for the given user
     */
    @Operation(summary = "Finds ratings for user as owner")
    @ApiResponse(responseCode = "400", description = "User not found in database")
    @GetMapping("/rating/{userID}/owner")
    public List<RatingDTO> getAsOwner(@PathVariable int userID) throws StatusCodeException {
        List<RatingDTO> ratings = ratingService.findRatingsAsOwnerByUserID(userID);
        if (userService.findUserByUserId(userID) == null){
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User not found in database");
        }
        return ratings;
    }

    /**
     * A method for finding the average rating of a user with a given userID
     * This takes into account both the ratings received as renter and as owner
     * @param userID The id of the user you want to find the average rating of
     * @return The average rating of the current user
     */
    @Operation(summary = "Finds average rating of user")
    @ApiResponse(responseCode = "400", description = "User not found in database")
    @GetMapping("/rating/{userID}/average")
    public float getAverageRating(@PathVariable int userID) throws StatusCodeException {
        if (userService.findUserByUserId(userID) != null) {
            return ratingService.findAverageRating(userID);
        } else throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User not found");
    }

    /**
     * Finds the average of the ratings the given user received as the owner of listings
     * @param userID The user we want to find the rating of
     * @return The average rating of the given user as the owner of listings
     */
    @Operation(summary = "Finds average rating of user as owner")
    @ApiResponse(responseCode = "200", description = "No ratings found for the user as owner")
    @ApiResponse(responseCode = "400", description = "User not found in database")
    @GetMapping("/rating/{userID}/average/owner")
    public float getAverageRatingAsOwner(@PathVariable int userID) throws StatusCodeException {
        if (userService.findUserByUserId(userID) != null) {
            try{
                return ratingService.findAverageRatingAsOwner(userID);
            }catch (Exception e){
                throw new StatusCodeException(HttpStatus.OK, "No ratings found");
            }
        } else throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User not found");
    }

    /**
     * Finds the average of the ratings the given user received as the renter of listings
     * @param userID The user we want to find the rating of
     * @return The average rating of the given user as the owner of listings
     */
    @Operation(summary = "Finds average rating of user as renter")
    @ApiResponse(responseCode = "200", description = "No ratings was found for the user as renter")
    @ApiResponse(responseCode = "400", description = "User not found in database")
    @GetMapping("/rating/{userID}/average/renter")
    public float getAverageRatingAsRenter(@PathVariable int userID) throws StatusCodeException {
        if (userService.findUserByUserId(userID) != null) {
            try {
                return ratingService.findAverageRatingAsRenter(userID);
            } catch (Exception e){
                throw new StatusCodeException(HttpStatus.OK, "No ratings found");
            }
        } else throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User not found");
    }

    /**
     * A method that transforms a given ratingDTO into a ratingDAO, then adds
     * this ratingDAO to the database
     * @param ratingDTO The ratingDTO with the information to be added to the database
     */
    @Operation(summary = "Saves rating to db")
    @ApiResponse(responseCode = "201", description = "Returns true if the rating was posted")
    @ApiResponse(responseCode = "401", description = "Token not found")
    @ApiResponse(responseCode = "400", description = "User not found in database")
    @ApiResponse(responseCode = "403", description = "User has already given a rating for this rent instance")
    @PostMapping("/rating/save")
    @RequireAuth
    public void postRating(@RequestBody RatingDTO ratingDTO) throws StatusCodeException {
        TokenDTO userToken;
        try {
            userToken = TokenUtil.getDataJWT();
        } catch (NullPointerException e) {
            throw new StatusCodeException(HttpStatus.UNAUTHORIZED, "No token found");
        }
        if (rentService.getRentFromId(ratingDTO.getRentID()) == null) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Rent not found");
        }
        int tokenUserID = userToken.getAccountId();
        if (ratingDTO.getScore() < 0 || ratingDTO.getScore() > 5) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Rating can not be less than 0 or more than 5");
        }
        if (userService.findUserByUserId(tokenUserID) != null){
            RatingDAO dao = new RatingDAO();
            dao.setComment(ratingDTO.getComment());
            dao.setScore(ratingDTO.getScore());
            dao.setRenterIsReceiverOfRating(ratingDTO.isRenterReceiverOfRating());
            dao.setRent(rentService.getRentFromId(ratingDTO.getRentID()));
            System.out.println(ratingIsGivenByCurrentUser(ratingDTO.getRentID()));
            if (!ratingIsGivenByCurrentUser(ratingDTO.getRentID())){
                ratingService.saveRating(dao);
                throw new StatusCodeException(HttpStatus.CREATED, "Rating posted!");
            } else {
                throw new StatusCodeException(HttpStatus.FORBIDDEN, "This user has already given a rating for this rent instance");
            }
        }
        throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Could not find user");
    }

    /**
     * Checks to see if the active user has given a rating for the rent with the given rent id
     * @param rentid The rent id we want to check
     * @return true if the current user has already given a rating
     *         false if not
     */
    @Operation(summary = "Checks to see if the current user has given a rating to the owner/renter of a rent")
    @ApiResponse(responseCode = "200", description = "User has not given by the user")
    @ApiResponse(responseCode = "302", description = "Rating has been given by the user")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "404", description = "Rent not found")
    @GetMapping("rating/{rentid}/israted")
    public boolean ratingIsGivenByCurrentUser(@PathVariable int rentid) throws StatusCodeException {
        TokenDTO userToken;
        try {
            userToken = TokenUtil.getDataJWT();
        } catch (NullPointerException e) {
            throw new StatusCodeException(HttpStatus.UNAUTHORIZED, "No token found");
        }
        int tokenUserID = userToken.getAccountId();
        try{
            rentService.getRentFromId(rentid);
        } catch (Exception e) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Rent not found");
        }
        return ratingService.userHasGivenRating(userService.findUserByUserId(tokenUserID), rentid);
    }
}
