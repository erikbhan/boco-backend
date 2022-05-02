package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.CommunityRequestDAO;
import no.ntnu.idatt2106.model.DAO.UserCommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.repository.CommunityRequestRepository;
import no.ntnu.idatt2106.repository.UserCommunityRepository;
import org.springframework.stereotype.Service;

@Service
public class CommunityRequestService {
    private final CommunityRequestRepository communityRequestRepository;
    private final UserCommunityRepository userCommunityRepository;
    CommunityRequestDAO communityRequestDAO = new CommunityRequestDAO();

    public CommunityRequestService(CommunityRequestRepository communityRequestRepository, UserCommunityRepository userCommunityRepository) {
        this.communityRequestRepository = communityRequestRepository;
        this.userCommunityRepository = userCommunityRepository;
    }

    public void addNewRequest(CommunityDAO communityDAO, UserDAO userDAO, String message) {
        communityRequestDAO.setCommunityID(communityDAO);
        communityRequestDAO.setUserID(userDAO);
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


}
