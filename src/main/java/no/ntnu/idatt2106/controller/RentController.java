package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.middleware.RequireAuth;
import no.ntnu.idatt2106.model.DAO.RentDAO;
import no.ntnu.idatt2106.model.DTO.TokenDTO;
import no.ntnu.idatt2106.service.RentService;
import no.ntnu.idatt2106.util.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/rent/userHistory")
    @Operation(summary = "Get the full list of rent objects which a user has rented")
    @ApiResponse(responseCode = "404", description = "User not found in the DB")
    public List<RentDAO> getRentHistoryOfUser() throws Exception {
        TokenDTO token = TokenUtil.getDataJWT();
        if(Integer.valueOf(token.getAccountId()) != null && Integer.valueOf(token.getAccountId()) > 0) {
            List<RentDAO> rentHistory = rentService
                    .findAllRentDAOWithRenterIdAndStatus(Integer.valueOf(token.getAccountId()), true);

            if(rentHistory != null && rentHistory.size() > 0) {

            }
            throw new StatusCodeException(HttpStatus.NOT_FOUND, "No rent history was found for a user with this id");
        }
        throw new StatusCodeException(HttpStatus.NOT_FOUND, "No valid user id found in token");
    }
}
