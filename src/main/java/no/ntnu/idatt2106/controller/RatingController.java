package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.model.DTO.RatingDTO;
import no.ntnu.idatt2106.service.RatingService;
import no.ntnu.idatt2106.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@ApiResponse(responseCode = "200", description = "OK")
public class RatingController {
    private final RatingService ratingService;
    private final UserService userService;

    public RatingController(RatingService ratingService, UserService userService) {
        this.ratingService = ratingService;
        this.userService = userService;
    }

    @Operation(summary = "Finds ratings for user as renter")
    @ApiResponse(responseCode = "404", description = "User not found in DB")
    @GetMapping("/rating/as_renter")
    public List<RatingDTO> getAsRenter(@RequestParam int userID) throws StatusCodeException {
        List<RatingDTO> ratings = ratingService.findRatingsAsRenterByUserID(userID);
        if (!ratings.isEmpty()){
            return ratings;
        } else throw new StatusCodeException(HttpStatus.OK, "User not found or had no ratings as renter");
    }

    @Operation(summary = "Finds ratings for user as owner")
    @ApiResponse(responseCode = "404", description = "User not found in DB")
    @GetMapping("/rating/as_owner")
    public List<RatingDTO> getAsOwner(@RequestParam int userID) throws StatusCodeException {
        List<RatingDTO> ratings = ratingService.findRatingsAsOwnerByUserID(userID);
        if (!ratings.isEmpty()){
            return ratings;
        } else throw new StatusCodeException(HttpStatus.OK, "User not found or had no ratings as owner");
    }
}
