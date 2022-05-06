package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.UserDTO;
import no.ntnu.idatt2106.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;


import no.ntnu.idatt2106.util.HashUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * A method to find a specific user by their unique user id.
     * @param userId The unique user id of the user you want to find
     * @return Returns a UserDAO of the user matching the input user id.
     */
    public UserDAO findUserByUserId(int userId) {
        return userRepository.findUserDAOByUserID(userId);
    }

    /**
     * A method to find a user by their email.
     * @param email The email of the user you want to find
     * @return Returns a UserDAO for the user with this email.
     */
    public UserDAO findUserByEmail(String email) {
        return userRepository.findUserDAOByEmail(email);
    }

    /**
     * A method to save a new user to the DB.
     * @param user The UserDAO of the new user you want to store in the DB.
     */
    public void saveUser(UserDAO user) {
        userRepository.save(user);
    }

    /**
     * Converts a list of user DAOs to a list of user DTOs
     * @param list A list of user DAOs
     * @return A list of user DTOs
     */
    public List<UserDTO> convertListUserDAOToListUserDTO(List<UserDAO> list) {
        List<UserDTO> convertedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            convertedList.add(new UserDTO(list.get(i)));
        }
        return convertedList;
    }

    /**
     * Change a password for a user
     * @param userDAO  User to change password for
     * @param password Password to change to
     * @return UserDAO of the user that has gotten password changed
     */
    public UserDAO changePasswordForUser(UserDAO userDAO, String password) {
        HashUtil hashUtil = new HashUtil();
        byte[] salt = hashUtil.getRandomSalt();
        byte[] hashedPassword = hashUtil.getHashedPassword(password, salt);

        userDAO.setSalt(Base64.getEncoder().encodeToString(salt));
        userDAO.setHash(Base64.getEncoder().encodeToString(hashedPassword));

        saveUser(userDAO);
        return userDAO;
    }

    /**
     * Method that authenticates password
     * @param user     User that has the password
     * @param password Password to authenticate
     * @return boolean that explains if the operation succeeded or not
     */
    public boolean attemptAuthenticationOfPassword(UserDAO user, String password) throws NoSuchAlgorithmException {
        if (user == null) {
            return false;
        }
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(Base64.getDecoder().decode(user.getSalt()));
        byte[] hashedPassword = Base64.getEncoder().encode(md.digest(password.getBytes(StandardCharsets.UTF_8)));

        if (new String(hashedPassword).equals(new String(user.getHash().getBytes(StandardCharsets.UTF_8)))) {
            return true;
        }
        return false;
    }

    /**
     * Method that clears all personal data from a user, this method is used when deleting a user to keep ratings given
     * @param userDAO User to clear
     */
    public void clearUserInfo(UserDAO userDAO) {
        userDAO.setFirstName("Slettet");
        userDAO.setLastName("Konto: " + userDAO.getUserID());
        userDAO.setPicture("");
        userDAO.setHash("");
        userDAO.setSalt("");
        userDAO.setAddress("");
        userDAO.setEmail("");
        userRepository.save(userDAO);
    }
}
