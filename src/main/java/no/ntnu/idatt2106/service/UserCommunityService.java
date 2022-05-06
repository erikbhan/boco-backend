package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserCommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.CommunityDTO;
import no.ntnu.idatt2106.repository.CommunityRepository;
import no.ntnu.idatt2106.repository.UserCommunityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserCommunityService {
    private CommunityService communityService;
    private CommunityRepository communityRepository;
    private UserCommunityRepository userCommunityRepository;
    private UserService userService;

    public UserCommunityService(CommunityRepository communityRepository, UserCommunityRepository userCommunityRepository, UserService userService, CommunityService communityService) {
        this.communityRepository = communityRepository;
        this.userCommunityRepository = userCommunityRepository;
        this.userService = userService;
        this.communityService = communityService;
    }


    /**
     * Checks if a user is a member of a community
     * @param user User to check member status for
     * @param communityDAO Community to check if the user is a part of
     * @return true if user is in community, false if user is not in community
     */
    public boolean userIsInCommunity(int user, CommunityDAO communityDAO){
        return (userCommunityRepository.isUser(user, communityDAO.getCommunityID()) == 1);
    }

    /**
     * Checks if a user is an admin in a community
     * @param userID User to check admin status for
     * @param communityID Community to check admin status for
     * @return true if admin, false if not admin
     */
    public boolean userIsAdminInCommunity(int userID, int communityID) {
        return userCommunityRepository.isAdmin(userID,communityID);
    }

    /**
     * Method to add a user to a community
     * @param user user to add to community
     * @param communityDAO Community to add user to
     * @return True if operation succeeded, false if it failed
     */
    public boolean addUserToCommunity(int user, CommunityDAO communityDAO){
           UserCommunityDAO userCommunity = new UserCommunityDAO(communityDAO, userService.findUserByUserId(user), false);
           try {
               userCommunityRepository.save(userCommunity);
           }
           catch (Exception e){
               return false;
           }
           return true;
    }

    /**
     * Method to add a user to a community and set them as an admin
     * @param user user to add to community
     * @param communityDAO Community to add user to
     * @return True if operation succeeded, false if it failed
     */
    public boolean addUserAsAdminToCommunity(int user, CommunityDAO communityDAO){
        UserCommunityDAO userCommunity = new UserCommunityDAO(communityDAO, userService.findUserByUserId(user), false);
        try {
            setAdmin(userCommunity);
            userCommunityRepository.save(userCommunity);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * Remove a user from a community
     * @param ucd UserCommunityDao to remove
     * @return true if succeeded, false if unsuccessful
     */
    public boolean removeUserFromCommunity(UserCommunityDAO ucd){
        if(userIsInCommunity(ucd.getUser().getUserID(), ucd.getCommunity())){

            userCommunityRepository.delete(ucd);
            return true;
        }
        return false;
    }

    /**
     * Method to set a user as an admin in a community
     * @param ucd UserCommunityDAO linking the user and community
     */
    public void setAdmin(UserCommunityDAO ucd){
        if(!ucd.isAdministrator()){
            ucd.setAdministrator(true);
        }
    }

    /**
     * Remove admin priveleges from a user
     * @param ucd UserCommunityDAO linking the user and community
     */
    public void retractAdmin(UserCommunityDAO ucd){
        ucd.setAdministrator(false);
    }

    /**
     * Gets a list of all communities that a user is part of
     * @param user User to check for communities
     * @return a list of all communities for a user
     */
    public ArrayList<CommunityDTO> getAllCommunitiesForUser(UserDAO user){
        List<UserCommunityDAO> communityList =  userCommunityRepository.findAllByUserID(user);
        ArrayList<CommunityDTO> communityDTOList = new ArrayList<>();
        for (int i = 0; i < communityList.size(); i++) {
            CommunityDAO communityDAO = communityRepository
                    .findCommunityDAOByCommunityID(communityList.get(i).getCommunity().getCommunityID());
            CommunityDTO communityDTO = new CommunityDTO(communityDAO);
            communityDTOList.add(communityDTO);
        }
        return communityDTOList;
    }

    /**
     * Deletes a user from all the communities they are a part of
     * @param user User to delete communities from
     */
    public void deleteUserFromAllGroups(UserDAO user) {
        List<UserCommunityDAO> communities = userCommunityRepository.findAllByUserID(user);
        userCommunityRepository.deleteAll(communities);
    }

    /**
     * Get a UserCommunityDAO object from userID and CommunityDAO
     * @param userId User that is a part of the community
     * @param communityDAO Community that has the user
     * @return A UserCommunityDAO object connecting the user and community
     */
    public UserCommunityDAO getByIds(int userId, CommunityDAO communityDAO) {
        List<UserCommunityDAO> communities = userCommunityRepository.findAllByUserID(userService.findUserByUserId(userId));
        for (UserCommunityDAO userCommunityDAO:communities) {
            if (userCommunityDAO.getCommunity().equals(communityDAO)) {
                return userCommunityDAO;
            }
        }
        return null;
    }

    /**
     * Find the amount of admins for a community
     * @param communityID Community to find amount of admins for
     * @return Integer showing the amount of admins for community
     */
    public int getAdminsSize(int communityID){
        return userCommunityRepository.countAdmins(communityID);
    }

    /**
     * Get all members of a community
     * @param community Community to find all members for
     * @return A list of all members of the community
     */
    public List<UserCommunityDAO> findAllMembersInACommunityByCommunity(CommunityDAO community) {
        return userCommunityRepository.findAllByCommunityID(community);
    }

    /**
     * Turn a list of UserCommunityDAOs into a list of UserDAOs
     * @param list List of UserCommunityDAOs to turn into users
     * @return List of UserDAOs
     */
    public List<UserDAO> makeListOfAllMembersInACommunity(List<UserCommunityDAO> list) {
        List<UserDAO> members = new ArrayList<>();
        for(int i = 0; i < list.size(); i++) {
            members.add(list.get(i).getUser());
        }
        return members;
    }

    /**
     * Find all communities that a user is admin of
     * @param userID User to check admin status for
     * @return A list of CommunityDAOs that the user is admin for
     */
    public List<CommunityDAO> getListOfAllAdminCommunities(int userID){
        List<CommunityDAO> communities = new ArrayList<>();
        List<Integer> adminCommunityIDs = userCommunityRepository.getAdminCommunityIDs(userID);
        for (int communityID : adminCommunityIDs){
            communities.add(communityService.findCommunityDAOByCommunityID(communityID));
        }
        return communities;
    }

    /**
     * Find all community IDs for communities that a user is admin of
     * @param userID User to check admin status for
     * @return A list of integers representing the IDs of communities that the user is admin for
     */
    public List<Integer> getIdOfAllAdminCommunities(int userID){
        return userCommunityRepository.getAdminCommunityIDs(userID);
    }
}
