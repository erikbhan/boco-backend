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

    /**
     * Adds a CommunityDAO to database
     * @param community CommunityDAO to add
     */
    public void addCommunity(CommunityDAO community) {
        communityRepository.save(community);
    }

    /**
     * Get a CommunityDAO by passing in a communityID
     * @param communityID communityID for community to find
     * @return CommunityDAO using the CommunityID
     */
    public CommunityDAO findCommunityDAOByCommunityID(int communityID) {
        return communityRepository.findCommunityDAOByCommunityID(communityID);
    }

    /**
     * Turn a CommunityDTO into a CommunityDAO
     * @param communityDTO CommunityDTO to turn into DAO
     * @return CommunityDAO that has been converted
     */
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

    /**
     * Find all communities that has a specific name
     * @param name name to search for
     * @return List of CommunityDAOs containing the name
     */
    public List<CommunityDAO> findAllCommunityDAOWithContainingAGivenName(String name) {
        return communityRepository.findAllByNameLike(name);
    }

    /**
     * Convert a list of CommunityDAOs to a list of CommunityDTOs
     * @param list list of CommunityDAOs to convert
     * @return list of converted CommunityDTOs
     */
    public List<CommunityDTO> convertListCommunityDAOToListCommunityDTO(List<CommunityDAO> list) {
        List<CommunityDTO> convertedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            convertedList.add(new CommunityDTO(list.get(i)));
        }
        return convertedList;
    }

    /**
     * Remove a communityDAO from the databse
     * @param communityDAO Community to remove
     * @return true if successful, false if unsuccessful
     */
    public boolean removeCommunity(CommunityDAO communityDAO) {
        try {
            communityRepository.delete(communityDAO);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get a list of all CommunityDAOs
     * @return a list of all CommunityDAOs
     */
    public List<CommunityDAO> findAll() {
        return communityRepository.findAll();
    }
}
