package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.model.DTO.CommunityDTO;
import no.ntnu.idatt2106.service.CommunityService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@ApiResponse(responseCode = "200")
public class CommunityController {
    private final CommunityService communityService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    /**
     * Adds community to database
     * @param communityDTO community transfer object for community to add.
     */
    @Operation(summary = "Add community to database")
    @ApiResponse(responseCode = "201")
    @ApiResponse(responseCode = "400")
    @PostMapping("/community/save")
    public void addCommunity(@RequestBody CommunityDTO communityDTO) {
        communityService.addCommunity(communityDTO);
    }

}
