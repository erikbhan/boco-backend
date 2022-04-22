package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.middleware.RequireAuth;
import no.ntnu.idatt2106.model.DAO.RentDAO;
import no.ntnu.idatt2106.model.DTO.TokenDTO;
import no.ntnu.idatt2106.service.RentService;
import no.ntnu.idatt2106.util.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class controls the api request related to renting objects.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
@ApiResponse(responseCode = "200")
@ApiResponse(responseCode = "401", description = "Not authenticated")
@RequireAuth
public class RentController {
    private final RentService rentService;

    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    /**
     * A method to get the full rent history for a user.
     * This method returns a list of all rent objects for the user.
     * @param userid The user id of the user you want the rent history for
     * @return Returns a list of all rent objects for the user.
     * @throws Exception
     */
    @GetMapping("/users/{userid}/profile/rent/userHistory")
    @Operation(summary = "Get the full list of rent objects which a user has rented")
    @ApiResponse(responseCode = "404", description = "User not found in the DB")
    public List<RentDAO> getRentHistoryOfUser(@PathVariable() int userid) throws Exception {
        if(userid > 0) {
            List<RentDAO> rentHistory = rentService
                    .findAllRentDAOWithRenterIdAndStatus(userid, true);

            if(rentHistory != null && rentHistory.size() > 0) {
                return rentHistory;
            }
            throw new StatusCodeException(HttpStatus.NOT_FOUND, "No rent history was found for a user with this id");
        }
        throw new StatusCodeException(HttpStatus.NOT_FOUND, "User id is not valid");
    }
}
