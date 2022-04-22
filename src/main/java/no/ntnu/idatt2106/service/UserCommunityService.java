package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserCommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.repository.CommunityRepository;
import no.ntnu.idatt2106.repository.UserCommunityRepository;

public class UserCommunityService {

    private CommunityRepository communityRepository;
    private UserCommunityRepository userCommunityRepository;
    private UserService userService;

    public UserCommunityService(CommunityRepository communityRepository, UserCommunityRepository userCommunityRepository, UserService userService) {
        this.communityRepository = communityRepository;
        this.userCommunityRepository = userCommunityRepository;
        this.userService = userService;
    }

    public void addUserToCommunity(UserDAO user, CommunityDAO communityDAO){
           UserCommunityDAO userCommunity = new UserCommunityDAO(communityDAO, user, false);
           userCommunityRepository.save(userCommunity);
    }
}
