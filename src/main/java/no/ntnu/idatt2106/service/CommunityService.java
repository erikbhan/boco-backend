package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DTO.CommunityDTO;
import no.ntnu.idatt2106.repository.CommunityRepository;
import org.springframework.stereotype.Service;

@Service
public class CommunityService {
    private final CommunityRepository communityRepository;


    public CommunityService(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    public void addCommunity(CommunityDAO community) {
        communityRepository.save(community);
    }

    public CommunityDAO findCommunityDAOByCommunityID(int communityID) {
        System.out.println("TRYING TO ACCESS A USER FROM USERID");
        return communityRepository.findCommunityDAOByCommunityID(communityID);
    }

    public CommunityDAO turnCommunityDTOIntoCommunityDAO(CommunityDTO communityDTO) {
        CommunityDAO community = new CommunityDAO();
        community.setDescription(communityDTO.getDescription());
        community.setLocation(communityDTO.getLocation());
        community.setDescription(communityDTO.getDescription());
        community.setName(communityDTO.getName());
        community.setPicture(communityDTO.getPicture());
        community.setVisibility(communityDTO.getVisibility());
        return community;
    }

}
