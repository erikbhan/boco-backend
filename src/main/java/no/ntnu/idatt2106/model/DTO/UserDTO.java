package no.ntnu.idatt2106.model.DTO;

import no.ntnu.idatt2106.model.DAO.UserDAO;

/**
 * A class representing the user object.
 * This class shall be returned to the frontend instead of the user dao.
 * This class contains necessary access-methods for the data it contains.
 */
public class UserDTO {
    String userId;
    String email;
    String firstName;
    String lastName;
    String address;
    String picture;

    public UserDTO(String userId, String email, String firstName, String lastName, String address, String picture) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.picture = picture;
    }

    public UserDTO(UserDAO userDAO) {
        this.userId = String.valueOf(userDAO.getUserID());
        this.email = userDAO.getEmail();
        this.firstName = userDAO.getFirstName();
        this.lastName = userDAO.getLastName();
        this.address = userDAO.getAddress();
        this.picture = userDAO.getPicture();
    }

    public UserDTO() {
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPicture() {
        return picture;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * A getter for the user id.
     * This method coverts the user id into a integer since it is stored as a string.
     * @return Returns a integer made from the value of the string user id.
     */
    public int getUserId() {return Integer.valueOf(userId);}

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
