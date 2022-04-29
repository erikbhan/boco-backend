package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.CommunityRequestDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.repository.CommunityRequestRepository;
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



}
