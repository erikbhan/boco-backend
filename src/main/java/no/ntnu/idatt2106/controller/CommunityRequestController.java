package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.middleware.RequireAuth;
import no.ntnu.idatt2106.service.CommunityRequestService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequireAuth
@ApiResponse(responseCode = "401", description = "Unauthorized")
public class CommunityRequestController {
    private CommunityRequestService communityRequestService;

    public CommunityRequestController(CommunityRequestService communityRequestService) {
        this.communityRequestService = communityRequestService;
    }


}
