package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.model.DAO.RentDAO;
import no.ntnu.idatt2106.model.DTO.RentDTO;
import no.ntnu.idatt2106.service.RentService;
import no.ntnu.idatt2106.model.DTO.TokenDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import no.ntnu.idatt2106.middleware.RequireAuth;
import no.ntnu.idatt2106.util.TokenUtil;
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
     * A method to get all rented objects for a user.
     * This method returns a list of all objects rented by the user.
     * @param userid The user id of the user you want the rent history for
     * @return Returns a list of all objects rented by the user.
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

    /**
     * A method to get the full rent history for a user.
     * This method returns a list of all rent objects for the user.
     * @param userid The user id of the user you want the rent history for
     * @return Returns a list of all rent objects for the user.
     * @throws Exception
     */
    @GetMapping("/users/{userid}/profile/rent/userHistory/all")
    @Operation(summary = "Get a list of all rent agreements for a user, both accepted and not.")
    @ApiResponse(responseCode = "404", description = "User not found in the DB")
    public List<RentDAO> getFullRentHistoryOfUser(@PathVariable() int userid) throws Exception {
        if(userid > 0) {
            List<RentDAO> rentHistory = rentService
                    .findAllRentDAOWithRenterId(userid);

            if(rentHistory != null && rentHistory.size() > 0) {
                return rentHistory;
            }
            throw new StatusCodeException(HttpStatus.NOT_FOUND, "No rent history was found for a user with this id");
        }
        throw new StatusCodeException(HttpStatus.NOT_FOUND, "User id is not valid");
    }

    /**
     * A method to get the full rent history for a owner.
     * This method returns a list of all rent objects listed by the owner.
     * @param userid The user id of the owner you want the rent history for
     * @return Returns a list of all rent objects listed by the user.
     * @throws Exception
     */
    @GetMapping("/users/{userid}/profile/rent/userHistory/owner/all")
    @Operation(summary = "Get a list of all rent agreements for a user, both accepted and not.")
    @ApiResponse(responseCode = "404", description = "User not found in the DB")
    public List<RentDAO> getFullRentHistoryOfOwner(@PathVariable() int userid) throws Exception {
        if(userid > 0) {
            List<RentDAO> rentHistory = rentService
                    .findAllRentDAOWithOwnerId(userid);

            if(rentHistory != null && rentHistory.size() > 0) {
                return rentHistory;
            }
            throw new StatusCodeException(HttpStatus.NOT_FOUND, "No rent history was found for a user with this id");
        }
        throw new StatusCodeException(HttpStatus.NOT_FOUND, "User id is not valid");
    }

    /**
     * A method to get the full rent history of all rented objects belonging to the owner.
     * @param userid The user id of the owner you want the rent history for
     * @return Returns a list of all rented objects listed by the owner which has also been rented by someone.
     * @throws Exception
     */
    @GetMapping("/users/{userid}/profile/rent/userHistory/owner")
    @Operation(summary = "Get a list of all rent agreements for a user, both accepted and not.")
    @ApiResponse(responseCode = "404", description = "User not found in the DB")
    public List<RentDAO> getRentHistoryOfOwner(@PathVariable() int userid) throws Exception {
        if(userid > 0) {
            List<RentDAO> rentHistoryFull = rentService
                    .findAllRentDAOWithOwnerId(userid);

            List<RentDAO> rentHistory = rentService
                    .filterAListOfRentDAOByStatusOfAgreement(rentHistoryFull,true);

            if(rentHistory != null && rentHistory.size() > 0) {
                return rentHistory;
            }
            throw new StatusCodeException(HttpStatus.NOT_FOUND, "No rent history was found for a user with this id");
        }
        throw new StatusCodeException(HttpStatus.NOT_FOUND, "User id is not valid");
    }

    /**
     * A method for saving a new rent agreement to the DB.
     * Returns a string with the success message or a http status error if the method fails.
     * @param rentDTO The format of the rent agreement.
     * @return Returns a string with the success message or a http status error if the method fails.
     * @throws StatusCodeException
     */
    @PostMapping("/renting/renter/save")
    @Operation(summary = "A method for saving a new rent agreement to the DB")
    @ApiResponse(responseCode = "406", description = "Make sure the token contains the account id")
    @ApiResponse(responseCode = "418", description = "Make sure there is no identical entity in the DB")
    public String saveRentingAgreementForRenter(@RequestBody RentDTO rentDTO) throws StatusCodeException {
        TokenDTO userToken = TokenUtil.getDataJWT();
        Integer tokenUserId = Integer.valueOf(userToken.getAccountId());
        if (tokenUserId != null) {
            RentDAO agreement = rentService.convertFromRentDTOTORentDAO(rentDTO);
            agreement.setRentID(tokenUserId);
            String saveAns = rentService.saveNewRentAgreementToDB(agreement);
            if (saveAns != null) {
                return saveAns;
            }
            throw new StatusCodeException(HttpStatus.I_AM_A_TEAPOT, "The save did not work");
        }
        throw new StatusCodeException(HttpStatus.NOT_ACCEPTABLE, "The token is missing the account id");
    }

    /**
     * Method for accepting a rent.
     * @param rentId ID for rent to be accepted
     */
    @Operation(summary = "Accepts rent")
    @ApiResponse(responseCode = "404", description = "Rent not found in DB")
    @PutMapping("/renting/{rentId}/accept")
    public String acceptRentRequest(@PathVariable() int rentId) throws StatusCodeException {
        RentDAO rent = rentService.getRentFromId(rentId);
        if (rent == null) {
            throw new StatusCodeException(HttpStatus.NOT_FOUND, "Could not find rent with ID: " + rentId);
        }
        rentService.acceptRent(rent);
        return "Accepted rent";
    }

    /**
     * Method for deleting a rent.
     * @param rentId ID for rent to be deleted
     */
    @Operation(summary = "Deletes rent")
    @ApiResponse(responseCode = "404", description = "Rent not found in DB")
    @PostMapping("/renting/{rentId}/delete")
    public String deleteRent(@PathVariable() int rentId) throws StatusCodeException {
        RentDAO rent = rentService.getRentFromId(rentId);
        if (rent == null) {
            throw new StatusCodeException(HttpStatus.NOT_FOUND, "Could not find rent with ID: " + rentId);
        }
        rentService.deleteRent(rentId);
        return "Deleted rent";
    }
}
