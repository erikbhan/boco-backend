package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.model.DTO.RentDTO;
import no.ntnu.idatt2106.service.RentService;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "Adds new rent")
    @ApiResponse(responseCode = "201", description = "Created")
    @PostMapping("/rent/add")
    public void addRent(@RequestBody RentDTO rentDTO) {
        rentService.saveRent(rentDTO);
    }
}
