package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.model.DTO.RentDTO;
import no.ntnu.idatt2106.service.RentService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class RentController {

    private final RentService rentService;

    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @PostMapping("/rent/add")
    public void addRent(@RequestBody RentDTO rentDTO) {
        rentService.saveRent(rentDTO);
    }

}
