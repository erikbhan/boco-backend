package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.CommunityRequestDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.repository.CommunityRequestRepository;
import no.ntnu.idatt2106.repository.UserCommunityRepository;
import org.springframework.stereotype.Service;

@Service
public class CommunityRequestService {
    private final CommunityRequestRepository communityRequestRepository;
    CommunityRequestDAO communityRequestDAO = new CommunityRequestDAO();

    public CommunityRequestService(CommunityRequestRepository communityRequestRepository) {
        this.communityRequestRepository = communityRequestRepository;
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

        communityRequestRepository.delete(reqNr);
    }



}
