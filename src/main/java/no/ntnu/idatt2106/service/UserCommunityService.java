package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserCommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.repository.CommunityRepository;
import no.ntnu.idatt2106.repository.UserCommunityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserCommunityService {
    private CommunityService communityService;
    private CommunityRepository communityRepository;
    private UserCommunityRepository userCommunityRepository;
    private UserService userService;

    public UserCommunityService(CommunityRepository communityRepository, UserCommunityRepository userCommunityRepository, UserService userService, CommunityService communityService) {
        this.communityRepository = communityRepository;
        this.userCommunityRepository = userCommunityRepository;
        this.userService = userService;
        this.communityService = communityService;
    }

    public boolean userIsInCommunity(int user, CommunityDAO communityDAO){
        return (userCommunityRepository.existsByUserID(userService.findUserByUserId(user)) && userCommunityRepository.existsByCommunityID(communityDAO));
    }
    public void addUserToCommunity(int user, CommunityDAO communityDAO){
           UserCommunityDAO userCommunity = new UserCommunityDAO(communityDAO, userService.findUserByUserId(user), false);
           userCommunityRepository.save(userCommunity);
    }
    public ArrayList<CommunityDAO> getAllCommunitiesForUser(UserDAO user){
        List<UserCommunityDAO> communityList =  userCommunityRepository.findAllByUserID(user);
        ArrayList<CommunityDAO> communityDAOList = new ArrayList<>();
        for (int i = 0; i < communityList.size(); i++) {
            CommunityDAO communityDAO = communityService.findCommunityDAOByCommunityID(communityList.get(i).getCommunityID().getCommunityID());
            communityDAOList.add(communityDAO);
        }
        return communityDAOList;
    }
}
