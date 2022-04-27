package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.model.DAO.RentDAO;
import no.ntnu.idatt2106.model.DTO.RentDTO;
import no.ntnu.idatt2106.service.ListingService;
import no.ntnu.idatt2106.service.NotificationService;
import no.ntnu.idatt2106.service.RentService;
import no.ntnu.idatt2106.model.DTO.TokenDTO;
import no.ntnu.idatt2106.service.UserService;
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
@CrossOrigin
@ApiResponse(responseCode = "401", description = "Not authenticated")
@RequireAuth
public class RentController {
    private final RentService rentService;

    ListingService listingService;
    NotificationService notificationService;
    UserService userService;

    public RentController(RentService rentService, ListingService listingService, NotificationService notificationService, UserService userService) {
        this.rentService = rentService;
        this.listingService = listingService;
        this.notificationService = notificationService;
        this.userService = userService;
    }

    /**
     * A method to get all rented objects for a user.
     * This method returns a list of all objects rented by the user.
     * @param userid The user id of the user you want the rent history for
     * @return Returns a list of all objects rented by the user.
     * @throws Exception
     */
    @GetMapping("/users/{userid}/profile/rent/history")
    @Operation(summary = "Get the full list of rent objects which a user has rented")
    @ApiResponse(responseCode = "200", description = "Returns the rent history of the user, deleted items are not included")
    @ApiResponse(responseCode = "400", description = "User or rent history not found in the DB")
    public List<RentDTO> getRentHistoryOfUser(@PathVariable() int userid) throws Exception {
        if(userid > 0) {
            List<RentDAO> rentHistoryDAO = rentService
                    .findAllRentDAOWithRenterIdAndStatus(userid, true);

            if(rentHistoryDAO != null && rentHistoryDAO.size() > 0) {
                rentHistoryDAO = rentService.filterListOfRentDAOOnDeleted(rentHistoryDAO);
                List<RentDTO> rentHistory = rentService
                        .convertListOfRentDAOToListOfRentDTO(rentHistoryDAO);
                return rentHistory;
            }
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "No rent history was found for a user with this id");
        }
        throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User id is not valid");
    }

    /**
     * A method to get the full rent history for a user.
     * This method returns a list of all rent objects for the user.
     * @param userid The user id of the user you want the rent history for
     * @return Returns a list of all rent objects for the user.
     * @throws Exception
     */
    @GetMapping("/users/{userid}/profile/rent/history/all")
    @Operation(summary = "Get a list of all rent agreements for a user, both accepted and not.")
    @ApiResponse(responseCode = "200", description = "Returns every instance where this user has rented an item from another user")
    @ApiResponse(responseCode = "400", description = "User or rent history not found in the DB")
    public List<RentDTO> getFullRentHistoryOfUser(@PathVariable() int userid) throws Exception {
        if(userid > 0) {
            List<RentDAO> rentHistoryDAO = rentService
                    .findAllRentDAOWithRenterId(userid);

            if(rentHistoryDAO != null && rentHistoryDAO.size() > 0) {
                List<RentDTO> rentHistory = rentService
                        .convertListOfRentDAOToListOfRentDTO(rentHistoryDAO);
                return rentHistory;
            }
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "No rent history was found for a user with this id");
        }
        throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User id is not valid");
    }

    /**
     * A method to get the full rent history for a owner.
     * This method returns a list of all rent objects listed by the owner.
     * @param userid The user id of the owner you want the rent history for
     * @return Returns a list of all rent objects listed by the user.
     * @throws Exception
     */
    @GetMapping("/users/{userid}/profile/rent/history/owner/all")
    @Operation(summary = "Get a list of all rent agreements for a user, both accepted and not.")
    @ApiResponse(responseCode = "200", description = "Returns every instance where this owner has rented out an item")
    @ApiResponse(responseCode = "400", description = "User or rent history not found in the DB")
    public List<RentDTO> getFullRentHistoryOfOwner(@PathVariable() int userid) throws Exception {
        if(userid > 0) {
            List<RentDAO> rentHistoryDAO = rentService
                    .findAllRentDAOWithOwnerId(userid);

            if(rentHistoryDAO != null && rentHistoryDAO.size() > 0) {
                List<RentDTO> rentHistory = rentService
                        .convertListOfRentDAOToListOfRentDTO(rentHistoryDAO);
                return rentHistory;
            }
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "No rent history was found for a user with this id");
        }
        throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User id is not valid");
    }

    /**
     * A method to get the full rent history of all rented objects belonging to the owner.
     * @param userid The user id of the owner you want the rent history for
     * @return Returns a list of all rented objects listed by the owner which has also been rented by someone.
     * @throws Exception
     */
    @GetMapping("/users/{userid}/profile/rent/history/owner")
    @Operation(summary = "Get a list of all rent agreements for a user, both accepted and not.")
    @ApiResponse(responseCode = "200", description = "Returns the rented item history of this owner, deleted items are not included")
    @ApiResponse(responseCode = "400", description = "User or rent history not found in the DB")
    public List<RentDTO> getRentHistoryOfOwner(@PathVariable() int userid) throws Exception {
        if(userid > 0) {
            List<RentDAO> rentHistoryFull = rentService
                    .findAllRentDAOWithOwnerId(userid);

            List<RentDAO> rentHistoryDAO = rentService
                    .filterAListOfRentDAOByStatusOfAgreement(rentHistoryFull,true);

            if(rentHistoryDAO != null && rentHistoryDAO.size() > 0) {
                rentHistoryDAO = rentService.filterListOfRentDAOOnDeleted(rentHistoryDAO);
                List<RentDTO> rentHistory = rentService
                        .convertListOfRentDAOToListOfRentDTO(rentHistoryDAO);
                return rentHistory;
            }
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "No rent history was found for a user with this id");
        }
        throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User id is not valid");
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
    @ApiResponse(responseCode = "200", description = "The rent agreement was saved to the DB")
    @ApiResponse(responseCode = "406", description = "The token does not contain the account id")
    @ApiResponse(responseCode = "418", description = "This entry already exists in the DB")
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
    @ApiResponse(responseCode = "200", description = "The status of the rent request was set to accepted")
    @ApiResponse(responseCode = "400", description = "Rent not found in DB")
    @PutMapping("/renting/{rentId}/accept")
    public String acceptRentRequest(@PathVariable() int rentId) throws StatusCodeException {
        RentDAO rent = rentService.getRentFromId(rentId);
        if (rent == null) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Could not find rent with ID: " + rentId);
        }
        rentService.acceptRent(rent);
        return "Accepted rent";
    }

    /**
     * Method for deleting a rent.
     * @param rentId ID for rent to be deleted
     */
    @Operation(summary = "Deletes rent")
    @ApiResponse(responseCode = "200", description = "The status of the rent request was set to deleted")
    @ApiResponse(responseCode = "400", description = "Rent not found in DB")
    @PutMapping("/renting/{rentId}/delete")
    public String deleteRent(@PathVariable() int rentId) throws StatusCodeException {
        RentDAO rent = rentService.getRentFromId(rentId);
        if (rent == null) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Could not find rent with ID: " + rentId);
        }
        rentService.deleteRent(rentId);
        return "Deleted rent";
    }
}
