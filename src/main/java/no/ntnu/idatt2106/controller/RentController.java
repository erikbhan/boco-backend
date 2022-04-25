package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.model.DAO.RentDAO;
import no.ntnu.idatt2106.service.RentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@ApiResponse(responseCode = "200", description = "OK")
public class RentController {

    private final RentService rentService;

    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    /**
     * Method for accepting a rent.
     * @param rentId ID for rent to be accepted
     */
    @Operation(summary = "Accepts rent")
    @ApiResponse(responseCode = "404", description = "Rent not found in DB")
    @PostMapping("/renting/renter/accept")
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
    @PostMapping("/renting/renter/delete")
    public String deleteRent(@PathVariable() int rentId) throws StatusCodeException {
        RentDAO rent = rentService.getRentFromId(rentId);
        if (rent == null) {
            throw new StatusCodeException(HttpStatus.NOT_FOUND, "Could not find rent with ID: " + rentId);
        }
        rentService.deleteRent(rentId);
        return "Deleted rent";
    }
}
