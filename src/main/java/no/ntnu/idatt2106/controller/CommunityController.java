package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.middleware.RequireAuth;
import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DTO.CommunityDTO;
import no.ntnu.idatt2106.model.DTO.TokenDTO;
import no.ntnu.idatt2106.service.CommunityService;
import no.ntnu.idatt2106.service.UserCommunityService;
import no.ntnu.idatt2106.util.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@ApiResponse(responseCode = "200")
@RequireAuth
public class CommunityController {
    private final CommunityService communityService;
    private final UserCommunityService userCommunityService;

    public CommunityController(CommunityService communityService, UserCommunityService userCommunityService) {
        this.communityService = communityService;
        this.userCommunityService = userCommunityService;
    }

    /**
     * Adds community to database
     * @param communityDTO community transfer object for community to add.
     */
    @Operation(summary = "Add community to database")
    @ApiResponse(responseCode = "201")
    @ApiResponse(responseCode = "400")
    @PostMapping("/community/save")
    public void addCommunity(@RequestBody CommunityDTO communityDTO) throws StatusCodeException {
        CommunityDAO communityDAO = communityService.turnCommunityDTOIntoCommunityDAO(communityDTO);
        TokenDTO userToken = TokenUtil.getDataJWT();
        Integer tokenUserId = Integer.valueOf(userToken.getAccountId());
        communityService.addCommunity(communityDAO);
        userCommunityService.addUserToCommunity(tokenUserId, communityDAO);
    }

}
