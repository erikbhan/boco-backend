package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DTO.CommunityDTO;
import no.ntnu.idatt2106.repository.CommunityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<CommunityDAO> findAllCommunityDAOWithGivenVisibility(int visibilityStatus) {
        return communityRepository.findAllByVisibility(visibilityStatus);
    }

    public List<CommunityDAO> findAllCommunityDAOWithContainingAGivenName(String name) {
        return communityRepository.findAllByNameLike(name);
    }

    public List<CommunityDTO> convertListCommunityDAOToListCommunityDTO(List<CommunityDAO> list) {
        List<CommunityDTO> convertedList = new ArrayList<>();
        for(int i = 0; i < list.size(); i++) {
            convertedList.add(new CommunityDTO(list.get(i)));
        }
        return convertedList;
    }

    public boolean removeCommunity(CommunityDAO communityDAO) {
//        try{
            communityRepository.delete(communityDAO);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }

        return true;
    }

    public List<CommunityDAO> findAll() {
        return communityRepository.findAll();
    }
}
