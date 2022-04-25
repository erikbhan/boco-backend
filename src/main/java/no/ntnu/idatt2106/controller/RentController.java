package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.service.RentService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiResponse(responseCode = "200", description = "Ok")
    @ApiResponse()
    @PostMapping("/rent/accept")
    public String acceptRentRequest(@RequestBody int rentId) {
        rentService.acceptRent(rentId);
        return "Accepted rent";
    }

    /**
     * Method for deleting a rent.
     * @param rentId ID for rent to be deleted
     */
    @Operation(summary = "Deletes rent")
    @ApiResponse(responseCode = "200", description = "Ok")
    @PostMapping("/rent/delete")
    public String deleteRent(@RequestBody int rentId) {
        rentService.deleteRent(rentId);
        return "Deleted rent";
    }
}
