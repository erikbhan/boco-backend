package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.middleware.RequireAuth;
import no.ntnu.idatt2106.service.ImageService;
import no.ntnu.idatt2106.util.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ImageController {

    private ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(
            value = "/images/{imageID}",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    @Operation(summary = "Get image", description = "Get image")
    @ApiResponse(responseCode = "404", description = "Image not found")
    @ApiResponse(responseCode = "200", description = "Image found")
    public @ResponseBody byte[] getImage(@PathVariable int imageID) throws StatusCodeException {
        byte[] img = imageService.getImage(imageID);
        if(img == null) throw new StatusCodeException(HttpStatus.NOT_FOUND, "Image not found");
        return img;
    }

    @PostMapping(
            value = "/images",
            consumes = MediaType.IMAGE_PNG_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Add image", description = "Add image")
    @ApiResponse(responseCode = "201", description = "Image added")
    @ApiResponse(responseCode = "400", description = "Image already exists")
    @RequireAuth
    public @ResponseBody int addImage(@RequestBody byte[] image) throws StatusCodeException {
        int accountId = TokenUtil.getDataJWT(TokenUtil.getToken()).getAccountId();
        int imageID = imageService.addImage(image, accountId);
        if(imageID == -1) throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Image already exists");
        return imageID;
    }

    @DeleteMapping(
            value = "/images/{imageID}"
    )
    @Operation(summary = "Delete image", description = "Delete image")
    @ApiResponse(responseCode = "404", description = "Image not found")
    @ApiResponse(responseCode = "200", description = "Image deleted")
    @ApiResponse(responseCode = "403", description = "User not allowed to delete image")
    @RequireAuth
    public void deleteImage(@PathVariable int imageID) throws StatusCodeException {
        int accountId = TokenUtil.getDataJWT(TokenUtil.getToken()).getAccountId();
        if(!imageService.ownsImage(imageID, accountId)) throw new StatusCodeException(HttpStatus.FORBIDDEN, "User does not own image");
        boolean deleted = imageService.deleteImage(imageID);
        if(!deleted) throw new StatusCodeException(HttpStatus.NOT_FOUND, "Image not found");
    }

}