package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.middleware.RequireAuth;
import no.ntnu.idatt2106.model.DAO.ListingDAO;
import no.ntnu.idatt2106.model.DAO.ListingPictureDAO;
import no.ntnu.idatt2106.model.DTO.TokenDTO;
import no.ntnu.idatt2106.service.ImageService;
import no.ntnu.idatt2106.service.ListingPictureService;
import no.ntnu.idatt2106.service.ListingService;
import no.ntnu.idatt2106.util.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@CrossOrigin
public class ImageController {

    private ImageService imageService;
    private ListingService listingService;
    private ListingPictureService listingPictureService;

    public ImageController(ImageService imageService, ListingService listingService, ListingPictureService listingPictureService) {
        this.imageService = imageService;
        this.listingService = listingService;
        this.listingPictureService = listingPictureService;
    }

    /**
     * Gets the image with the given image id
     * @param imageID The id of the requested image
     * @return an image
     */
    @Operation(summary = "Get image")
    @ApiResponse(responseCode = "400", description = "Image not found")
    @GetMapping(
            value = "/images/{imageID}",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody byte[] getImage(@PathVariable int imageID) throws StatusCodeException {
        byte[] img = imageService.getImage(imageID);
        if(img == null) throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Image not found");
        return img;
    }

    /**
     * Adds an image to the database
     * @param image The image to be added
     * @return the id of the image that was added
     */
    @RequireAuth
    @Transactional
    @Operation(summary = "Add image")
    @PostMapping(
            value = "/images",
            consumes = MediaType.IMAGE_PNG_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody int addImage(@RequestBody byte[] image){
        int accountId = TokenUtil.getDataJWT(TokenUtil.getToken()).getAccountId();
        int imageID = imageService.addImage(image, accountId);
        return imageID;
    }

    /**
     * Deletes an image from the database
     * @param imageID The id of the image you want to delete
     *
     */
    @RequireAuth
    @Operation(summary = "Delete image")
    @ApiResponse(responseCode = "400", description = "Image not found")
    @ApiResponse(responseCode = "403", description = "User not owner of image, not allowed to delete")
    @DeleteMapping(value = "/images/{imageID}")
    public void deleteImage(@PathVariable int imageID) throws StatusCodeException {
        int accountId = TokenUtil.getDataJWT(TokenUtil.getToken()).getAccountId();
        if(!imageService.ownsImage(imageID, accountId)) throw new StatusCodeException(HttpStatus.FORBIDDEN, "User does not own image");
        boolean deleted = imageService.deleteImage(imageID);
        if(!deleted) throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Image not found");
    }

    /**
     * Adds multiple image links to the database, and connects them to a listing
     * @param images A list of image links to be added
     */
    @RequireAuth
    @Operation(summary = "adds pictures to users last added listing")
    @ApiResponse(responseCode = "200", description = "Pictures added")
    @ApiResponse(responseCode = "400", description = "No image was received")
    @PostMapping(value = "/listing/pictures")
    public void addImagesToListing(@RequestBody List<String> images) throws StatusCodeException {
        if (images.size()==0){
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "No images was received");
        }
        TokenDTO userToken = TokenUtil.getDataJWT();
        int tokenUserId = userToken.getAccountId();
        ListingDAO listing = listingService.getUsersLastPostedListing(tokenUserId);
        for (String image:images){
            ListingPictureDAO picture = new ListingPictureDAO(image, listing);
            listingPictureService.save(picture);
        }
        throw new StatusCodeException(HttpStatus.OK, "pictures added");
    }

    /**
     * Method to change images in a listing
     * @param images the images the user wants to change to
     * @param listingId the listing id of the listing the user wants to change
     */
    @RequireAuth
    @Operation(summary = "Changes the listings images to the given list")
    @ApiResponse(responseCode = "200", description = "Listing pictures updated")
    @PutMapping(value = "/listing/{listingId}/pictures")
    public void changeListingsImages(@RequestBody List<String> images, @PathVariable int listingId) throws StatusCodeException {
        ListingDAO listing = listingService.findListingByListingId(listingId);
        listingPictureService.changeListingsPictures(images,listing);
        throw new StatusCodeException(HttpStatus.OK, "Listing Pictures updated");
    }
}