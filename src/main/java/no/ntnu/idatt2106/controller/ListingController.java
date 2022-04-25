package no.ntnu.idatt2106.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import no.ntnu.idatt2106.model.DAO.ListingDAO;
import no.ntnu.idatt2106.model.DTO.ListingDTO;
import no.ntnu.idatt2106.repository.ListingRepository;
import no.ntnu.idatt2106.service.ListingService;


@RequestMapping("/api")
@RestController
@CrossOrigin
public class ListingController {
    private final ListingService listingService;

    public ListingController(ListingService listingService){
        this.listingService = listingService;
    }

    @PostMapping("/listing")
    public ResponseEntity<String> postListing(@RequestBody ListingDTO listingDTO){
        System.out.println("\n\n\n-----------------" +listingDTO.getUserID() + "---------------\n\n");
        try{
            listingService.saveListing(listingDTO);
            return new ResponseEntity<String>("Annonse opprettet", HttpStatus.CREATED);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("Kunne ikke opprette", HttpStatus.BAD_REQUEST);
        }
    }
}
