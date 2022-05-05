package no.ntnu.idatt2106.model.DAO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class functions as a representation of the table user in the DB.
 * All fields in the user table is represented in this class, with access methods for everyone.
 */
@Entity
@Table(name = "user", schema = "public")
public class UserDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int userID;
    @Column(name = "email")
    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "address")
    private String address;
    @Column(name = "picture")
    private String picture;
    @Column(name= "salt")
    private String salt;
    @Column(name = "hash")
    private String hash;

    public UserDAO(String email, String firstName, String lastName, String address, String picture, String salt, String hash) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.picture = picture;
        this.salt = salt;
        this.hash = hash;
    }

    public UserDAO(int userID, String email, String firstName, String lastName, String address, String picture, String salt, String hash) {
        this.userID = userID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.picture = picture;
        this.salt = salt;
        this.hash = hash;
    }

    public UserDAO() {
    }

    public int getUserID() {
        return this.userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {return this.address;}

    public void setAddress(String address) {this.address = address;}

    public String getPicture() {return this.picture;}

    public void setPicture(String picture) {this.picture = picture;}

    public String getSalt() {return salt;}

    public void setSalt(String salt) {this.salt = salt;}

    public String getHash() {return hash;}

    public void setHash(String hash) {this.hash = hash;}
    
}