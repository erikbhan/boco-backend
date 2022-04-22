package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.repository.CommunityRepository;

public class CommunityService {
    private final CommunityRepository communityRepository;

    public CommunityService(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    public CommunityDAO findCommunityDAOByCommunityID(int communityID) {
        System.out.println("TRYING TO ACCESS A USER FROM USERID");
        return communityRepository.findCommunityDAOByCommunityID(communityID);
    }

}
