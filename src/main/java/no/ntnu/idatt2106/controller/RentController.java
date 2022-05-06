package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.model.DAO.RentDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.RentDTO;
import no.ntnu.idatt2106.service.ListingService;
import no.ntnu.idatt2106.service.NotificationService;
import no.ntnu.idatt2106.service.RentService;
import no.ntnu.idatt2106.service.UserService;
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
@CrossOrigin
@ApiResponse(responseCode = "401", description = "Unauthorized")
@RequireAuth
public class RentController {
    private final RentService rentService;
    private final UserService userService;

    public RentController(RentService rentService,UserService userService) {
        this.rentService = rentService;
        this.userService = userService;
    }

    /**
     * A method to get all rented objects for a user.
     * This method returns a list of all objects rented by the user.
     * @return Returns a list of all objects rented by the user.
     */
    @GetMapping("/user/profile/rent/history")
    @Operation(summary = "Get the full list of rent objects which a user has rented")
    @ApiResponse(responseCode = "400", description = "User not found in the database")
    public List<RentDTO> getRentHistoryOfUser() throws Exception {
        TokenDTO userToken = TokenUtil.getDataJWT();
        Integer tokenUserId = Integer.valueOf(userToken.getAccountId());
        UserDAO userDAO = userService.findUserByUserId(tokenUserId);
        if(userDAO != null) {
            List<RentDAO> rentHistoryDAO = rentService
                    .findAllRentDAOWithRenterIdAndStatus(userDAO.getUserID(), true);
            rentHistoryDAO = rentService.filterListOfRentDAOOnDeleted(rentHistoryDAO);
            List<RentDTO> rentHistory = rentService
                    .convertListOfRentDAOToListOfRentDTO(rentHistoryDAO);
            return rentHistory;
        }
        throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User id is not valid");
    }

    /**
     * A method to get the full rent history for a user.
     * This method returns a list of all rent objects for the user.
     * @return Returns a list of all rent objects for the user.
     */
    @GetMapping("/user/profile/rent/history/all")
    @Operation(summary = "Get a list of all rent agreements for a user, both accepted and not.")
    @ApiResponse(responseCode = "400", description = "User not found in the database")
    public List<RentDTO> getFullRentHistoryOfUser() throws Exception {
        TokenDTO userToken = TokenUtil.getDataJWT();
        Integer tokenUserId = Integer.valueOf(userToken.getAccountId());
        UserDAO userDAO = userService.findUserByUserId(tokenUserId);
        if(userDAO != null) {
            List<RentDAO> rentHistoryDAO = rentService
                    .findAllRentDAOWithRenterId(userDAO.getUserID());
            List<RentDTO> rentHistory = rentService
                    .convertListOfRentDAOToListOfRentDTO(rentHistoryDAO);
            return rentHistory;
        }
        throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User id is not valid");
    }

    /**
     * A method to get the full rent history for a owner.
     * This method returns a list of all rent objects listed by the owner.
     * @return Returns a list of all rent objects listed by the user.
     */
    @GetMapping("/user/profile/rent/history/owner/all")
    @Operation(summary = "Get a list of all rent agreements for a user, both accepted and not.")
    @ApiResponse(responseCode = "400", description = "User not found in the database")
    public List<RentDTO> getFullRentHistoryOfOwner() throws Exception {
        TokenDTO userToken = TokenUtil.getDataJWT();
        Integer tokenUserId = Integer.valueOf(userToken.getAccountId());
        UserDAO userDAO = userService.findUserByUserId(tokenUserId);
        if(userDAO != null) {
            List<RentDAO> rentHistoryDAO = rentService
                    .findAllRentDAOWithOwnerId(userDAO.getUserID());
            List<RentDTO> rentHistory = rentService
                    .convertListOfRentDAOToListOfRentDTO(rentHistoryDAO);
            return rentHistory;
        }
        throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User id is not valid");
    }

    /**
     * A method to get the full rent history of all rented objects belonging to the owner.
     * @return Returns a list of all rented objects listed by the owner which has also been rented by someone.
     */
    @GetMapping("/user/profile/rent/history/owner")
    @Operation(summary = "Get a list of all rent agreements for a user, only the accepted ones")
    @ApiResponse(responseCode = "400", description = "User not found in the database")
    public List<RentDTO> getRentHistoryOfOwner() throws Exception {
        TokenDTO userToken = TokenUtil.getDataJWT();
        Integer tokenUserId = Integer.valueOf(userToken.getAccountId());
        UserDAO userDAO = userService.findUserByUserId(tokenUserId);
        if(userDAO != null) {
            List<RentDAO> rentHistoryFull = rentService
                    .findAllRentDAOWithOwnerId(userDAO.getUserID());
            List<RentDAO> rentHistoryDAO = rentService
                    .filterAListOfRentDAOByStatusOfAgreement(rentHistoryFull,true);
            rentHistoryDAO = rentService.filterListOfRentDAOOnDeleted(rentHistoryDAO);
            List<RentDTO> rentHistory = rentService
                    .convertListOfRentDAOToListOfRentDTO(rentHistoryDAO);
            return rentHistory;
        }
        throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User id is not valid");
    }

    /**
     * A method for saving a new rent agreement to the DB.
     * Returns a string with the success message or a http status error if the method fails.
     * @param rentDTO The format of the rent agreement.
     * @return Returns a string with the success message or a http status error if the method fails.
     */
    @PostMapping("/renting/renter/save")
    @Operation(summary = "A method for saving a new rent agreement to the DB")
    @ApiResponse(responseCode = "200", description = "The rent agreement was saved to the DB")
    @ApiResponse(responseCode = "401", description = "Token not found")
    @ApiResponse(responseCode = "418", description = "This entry already exists in the DB")
    public String saveRentingAgreementForRenter(@RequestBody RentDTO rentDTO) throws StatusCodeException {
        TokenDTO userToken = TokenUtil.getDataJWT();
        Integer tokenUserId = Integer.valueOf(userToken.getAccountId());
        if (tokenUserId != null) {
            RentDAO agreement = rentService.convertFromRentDTOTORentDAO(rentDTO);
            UserDAO userDAO = userService.findUserByUserId(tokenUserId);
            agreement.setRenter(userDAO);
            String saveAns = rentService.saveNewRentAgreementToDB(agreement);
            if (saveAns != null) {
                return saveAns;
            }
            throw new StatusCodeException(HttpStatus.I_AM_A_TEAPOT, "The save did not work");
        }
        throw new StatusCodeException(HttpStatus.UNAUTHORIZED, "Token not found");
    }

    /**
     * Method for accepting a rent.
     * @param rentId ID for rent to be accepted
     */
    @Operation(summary = "Accepts rent")
    @ApiResponse(responseCode = "400", description = "Rent not found in DB")
    @PutMapping("/renting/{rentId}/accept")
    public void acceptRentRequest(@PathVariable() int rentId) throws StatusCodeException {
        RentDAO rent = rentService.getRentFromId(rentId);
        if (rent == null) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Could not find rent with ID: " + rentId);
        }
        TokenDTO tokenDTO = TokenUtil.getDataJWT();
        if(rent.getListing().getUser().getUserID() != tokenDTO.getAccountId()) throw new StatusCodeException(HttpStatus.FORBIDDEN, "Only the owner can delete a rent request");
        rentService.acceptRent(rent);
    }

    /**
     * Method for deleting a rent.
     * @param rentId ID for rent to be deleted
     */
    @Operation(summary = "Deletes rent")
    @ApiResponse(responseCode = "400", description = "Rent not found in DB")
    @DeleteMapping("/renting/{rentId}/delete")
    public void deleteRent(@PathVariable() int rentId) throws StatusCodeException {
        RentDAO rent = rentService.getRentFromId(rentId);
        if (rent == null) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Could not find rent with ID: " + rentId);
        }
        TokenDTO tokenDTO = TokenUtil.getDataJWT();
        if(rent.getListing().getUser().getUserID() != tokenDTO.getAccountId()) throw new StatusCodeException(HttpStatus.FORBIDDEN, "Only the owner can delete a rent request");
        rentService.deleteRent(rentId);
    }

    /**
     * Finds all rents where the user is either the renter or the owner
     * @return An array of rents
     */
    @Operation(summary = "Returns all rents where the user is either a renter or a owner")
    @GetMapping("/renting/all")
    public RentDTO[] getAllRents() {
        return rentService.getAllRents();
    }

    /**
     * Finds all rents between the active user and the given user, regardless of their roles in the different rents
     * @param userID The user id of the other user to search for
     * @return An array of rents
     */
    @Operation(summary = "Returns all rents between two users")
    @GetMapping("/renting/user/{userID}/all")
    public RentDTO[] getAllRentsUser(@PathVariable int userID) {
        TokenDTO userToken = TokenUtil.getDataJWT();
        int id = userToken.getAccountId();
        return rentService.getAllRents(userID, id);
    }
}
