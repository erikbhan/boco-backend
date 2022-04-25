package no.ntnu.idatt2106.model.DTO;

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

    public int getUserId() {return Integer.valueOf(userId);}

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
