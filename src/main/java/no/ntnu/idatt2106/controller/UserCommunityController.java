package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.repository.CommunityRepository;
import no.ntnu.idatt2106.service.UserCommunityService;
import no.ntnu.idatt2106.service.UserService;

public class UserCommunityController {
    private final UserCommunityService userCommunityService;
    private UserService userService;
    private CommunityRepository communityRepository;

    public UserCommunityController(UserCommunityService userCommunityService) {
        this.userCommunityService = userCommunityService;
    }

    public void addUserToCommunity(int id, int communityID){
        UserDAO user =  userService.findUserByUserId(id);
        CommunityDAO communityDAO = communityRepository.findCommunityDAOByCommunityID(communityID);
        userCommunityService.addUserToCommunity(user, communityDAO);
    }





}
