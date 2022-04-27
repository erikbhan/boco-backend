package no.ntnu.idatt2106.model.DTO;

import no.ntnu.idatt2106.model.DAO.UserDAO;

public class PublicUserDTO {
    int userId;
    String firstName;
    String lastName;
    String picture;

    public PublicUserDTO(int userId,String firstName, String lastName, String picture) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.picture = picture;
    }

    public PublicUserDTO(UserDAO userDAO) {
        this.userId = userDAO.getUserID();
        this.firstName = userDAO.getFirstName();
        this.lastName = userDAO.getLastName();
        this.picture = userDAO.getPicture();

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPicture() {
        return picture;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getUserId() {return userId;}

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
