package no.ntnu.idatt2106.model.DTO;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;


public class CommunityRequestDTO {

    private String text;

    private UserDAO userID;

    private CommunityDAO communityID;

    public CommunityRequestDTO() {
    }

    public CommunityRequestDTO(String text, UserDAO userID, CommunityDAO communityID) {
        this.text = text;
        this.userID = userID;
        this.communityID = communityID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserDAO getUserID() {
        return userID;
    }

    public void setUserID(UserDAO userID) {
        this.userID = userID;
    }

    public CommunityDAO getCommunityID() {
        return communityID;
    }

    public void setCommunityID(CommunityDAO communityID) {
        this.communityID = communityID;
    }
}
