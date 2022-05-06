package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.CommunityRequestDAO;
import no.ntnu.idatt2106.model.DAO.UserCommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.UserDTO;
import no.ntnu.idatt2106.repository.CommunityRepository;
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

    /**
     * Method to add a new request to join a community
     * @param communityDAO the community the user wants to join
     * @param userDAO the user wanting to join the community
     * @param message a message to the community's admin
     */
    public void addNewRequest(CommunityDAO communityDAO, UserDAO userDAO, String message) {
        communityRequestDAO.setCommunity(communityDAO);
        communityRequestDAO.setUser(userDAO);
        communityRequestDAO.setText(message);

        communityRequestRepository.save(communityRequestDAO);
    }

    /**
     *  A method to find the id of the request in the database via user and community ids
     * @param user_id the user
     * @param community_id the community you want
     * @return the id of the community request in the database
     */
    public int findRequest(int user_id, int community_id){
        return communityRequestRepository.findId(user_id, community_id);
    }

    /**
     *  A method to remove a request from the database
     * @param user_id of the user request to be removed
     * @param community_id of the community the request shall be removed from
     */
    public void removeRequest(int user_id, int community_id){
        int reqNr = findRequest(user_id, community_id);
        CommunityRequestDAO communityRequestDAO = communityRequestRepository.getById(reqNr);
        communityRequestRepository.delete(communityRequestDAO);
    }

    /**
     * A method to accept a users request to join a community
     * @param userDAO the user that will be accepted
     * @param communityDAO the community the user will become part of
     */
    public void acceptRequest(UserDAO userDAO, CommunityDAO communityDAO){
        UserCommunityDAO userCommunityDAO = new UserCommunityDAO(communityDAO,userDAO, false);
        userCommunityRepository.save(userCommunityDAO);
    }

    /**
     * A method to retrieve all the users with requests in a community
     * @param communityId of the community the user wants to see the requests in
     * @return a list of all the requests in the given community
     */
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

    /**
     * A method to get all the communityrequest objects
     * @param community of the community the user wants to see the requests in
     * @return a list of CommunityRequestDAOS from that community
     */
    public List<CommunityRequestDAO> getRequestsByCommunity(CommunityDAO community){
        return communityRequestRepository.findCommunityRequestDAOSByCommunity(community);
    }

    /**
     * Finds a given communityRequestDAO via the id
     * @param id the id of the communityRequest
     * @return the object matching the id
     */
    public CommunityRequestDAO getById(int id){
        return communityRequestRepository.findByCommunityRequestID(id);
    }

    /**
     * A method to delete all community request for a user
     * @param userDAO of the user that will remove all their requests
     */
    public void deleteRequestsForUser(UserDAO userDAO) {
        List<CommunityRequestDAO> requests = communityRequestRepository.findAllByUser(userDAO);
        for(CommunityRequestDAO communityRequestDAO:requests) {
            communityRequestRepository.delete(communityRequestDAO);
        }
    }
}
