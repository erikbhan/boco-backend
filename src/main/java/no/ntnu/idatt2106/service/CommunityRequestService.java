package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.CommunityRequestDAO;
import no.ntnu.idatt2106.repository.CommunityRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityRequestService {
    private final CommunityRequestRepository communityRequestRepository;

    public CommunityRequestService(CommunityRequestRepository communityRequestRepository) {
        this.communityRequestRepository = communityRequestRepository;
    }

    public List<CommunityRequestDAO> getRequestsByCommunity(CommunityDAO community){
        return communityRequestRepository.findCommunityRequestDAOSByCommunity(community);
    }

    public CommunityRequestDAO getById(int id){
        return communityRequestRepository.findByCommunityRequestID(id);
    }
}
