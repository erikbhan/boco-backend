package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.CommunityRequestDAO;
import no.ntnu.idatt2106.model.DAO.UserCommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.UserDTO;
import no.ntnu.idatt2106.repository.CommunityRepository;
import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.CommunityRequestDAO;
import no.ntnu.idatt2106.repository.CommunityRequestRepository;
import no.ntnu.idatt2106.repository.UserCommunityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommunityRequestService {
    private final CommunityRequestRepository communityRequestRepository;
    private final UserCommunityRepository userCommunityRepository;
    private final CommunityRepository communityRepository;
    private final UserService userService;
    CommunityRequestDAO communityRequestDAO = new CommunityRequestDAO();

    public CommunityRequestService(CommunityRequestRepository communityRequestRepository, UserCommunityRepository userCommunityRepository, CommunityRepository communityRepository, UserService userService) {
        this.communityRequestRepository = communityRequestRepository;
        this.userCommunityRepository = userCommunityRepository;
        this.communityRepository = communityRepository;
        this.userService = userService;
    }

    public void addNewRequest(CommunityDAO communityDAO, UserDAO userDAO, String message) {
        communityRequestDAO.setCommunity(communityDAO);
        communityRequestDAO.setUser(userDAO);
        communityRequestDAO.setText(message);

        communityRequestRepository.save(communityRequestDAO);
    }

    public int  findRequest(int user_id, int community_id){
        return communityRequestRepository.findId(user_id, community_id);
    }

    public void removeRequest(int user_id, int community_id){
        int reqNr = findRequest(user_id, community_id);
        CommunityRequestDAO communityRequestDAO = communityRequestRepository.getById(reqNr);
        communityRequestRepository.delete(communityRequestDAO);
    }

    public void acceptRequest(UserDAO userDAO, CommunityDAO communityDAO){
        UserCommunityDAO userCommunityDAO = new UserCommunityDAO(communityDAO,userDAO, false);
        userCommunityRepository.save(userCommunityDAO);
    }

    public List<UserDTO> getRequestsForCommunity(int communityId){
        CommunityDAO communityDAO = communityRepository.findCommunityDAOByCommunityID(communityId);
        List<CommunityRequestDAO> communityRequestDAOS = communityRequestRepository.findAllByCommunity(communityDAO);
        ArrayList<UserDTO> userDTOS = new ArrayList<>();
        for (int i = 0; i < communityRequestDAOS.size(); i++) {
           UserDTO userDTO = new UserDTO(communityRequestDAOS.get(i).getUser());
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    public List<CommunityRequestDAO> getRequestsByCommunity(CommunityDAO community){
        return communityRequestRepository.findCommunityRequestDAOSByCommunity(community);
    }

    public CommunityRequestDAO getById(int id){
        return communityRequestRepository.findByCommunityRequestID(id);
    }
}
