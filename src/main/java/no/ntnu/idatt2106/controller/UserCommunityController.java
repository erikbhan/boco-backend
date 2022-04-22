package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.repository.CommunityRepository;
import no.ntnu.idatt2106.service.UserCommunityService;
import no.ntnu.idatt2106.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class UserCommunityController {
    private final UserCommunityService userCommunityService;
    private final UserService userService;
    private final CommunityRepository communityRepository;

    public UserCommunityController(UserCommunityService userCommunityService, UserService userService, CommunityRepository communityRepository) {
        this.userCommunityService = userCommunityService;
        this.userService = userService;
        this.communityRepository = communityRepository;
    }

    public void addUserToCommunity(int id, int communityID){
        UserDAO user =  userService.findUserByUserId(id);
        CommunityDAO communityDAO = communityRepository.findCommunityDAOByCommunityID(communityID);
        userCommunityService.addUserToCommunity(user, communityDAO);
    }





}
