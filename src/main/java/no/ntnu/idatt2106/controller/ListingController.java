package no.ntnu.idatt2106.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import no.ntnu.idatt2106.model.DAO.ListingDAO;
import no.ntnu.idatt2106.model.DTO.ListingDTO;
import no.ntnu.idatt2106.repository.ListingRepository;
import no.ntnu.idatt2106.service.ListingCategoryService;
import no.ntnu.idatt2106.service.ListingService;


@RequestMapping("/api")
@RestController
@CrossOrigin
public class ListingController {
    private final ListingService listingService;
    private final ListingCategoryService listingCategoryService;

    public ListingController(ListingService listingService, ListingCategoryService listingCategoryService){
        this.listingService = listingService;
        this.listingCategoryService = listingCategoryService;
    }

    @PostMapping("/listing")
    public ResponseEntity<ListingDTO> postListing(@RequestBody ListingDTO listingDTO){
        listingService.saveListing(listingDTO);
        try{
            // for(String categoryName: listingDTO.getCategoryNames()){
            //     listingCategoryService.saveListingCategory(categoryName, listingDTO.);
            // }
            return new ResponseEntity<ListingDTO>(listingDTO, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<ListingDTO>(listingDTO, HttpStatus.BAD_REQUEST);
        }
    }
}
