package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserCommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.repository.CommunityRepository;
import no.ntnu.idatt2106.repository.UserCommunityRepository;

public class AddUserToCommunityService {



    private CommunityRepository communityRepository;
    private UserCommunityRepository userCommunityRepository;
    private UserService userService;

    public AddUserToCommunityService(CommunityRepository communityRepository, UserCommunityRepository userCommunityRepository, UserService userService) {
        this.communityRepository = communityRepository;
        this.userCommunityRepository = userCommunityRepository;
        this.userService = userService;
    }

    public void addUserToCommunityService(int id, int communityID){
       UserDAO user =  userService.findUserByUserId(id);
        CommunityDAO communityDAO = communityRepository.findCommunityDAOByCommunityID(id);
       if(user!=null){
           UserCommunityDAO userCommunity = new UserCommunityDAO(communityDAO, user, false);
           userCommunityRepository.save(userCommunity);
           //Add User to community
       }


    }
}
