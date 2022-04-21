package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * A class meant to use the access-point made to the User table in the DB.
 * This class uses methods from the {@link no.ntnu.idatt2106.repository.UserRepository UserRepository}
 */
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
        System.out.println("TRYING TO ACCESS A USER FROM USERID");
        return userRepository.findUserDAOByUserID(userId);
    }

    /**
     * A method to find a user by their email.
     * @param email The email of the user you want to find
     * @return Returns a UserDAO for the user with this email.
     */
    public UserDAO findUserByEmail(String email) {
        System.out.println("FINDING USER WITH EMAIL: " + email);
        return userRepository.findUserDAOByEmail(email);
    }

    /**
     * A method for finding the full name of a user.
     * @param userId The user id of the user
     * @return Returns the full name of the user with the specific user id.
     */
    public String findFullNameFromUserId(int userId) {
        System.out.println("FINDING THE FULL NAME OF THE USER");
        UserDAO user = userRepository.findUserDAOByUserID(userId);
        if(user == null) {
            return "No such user";
        }
        String fullName = user.getFirstName() + " " + user.getLastName();
        return fullName;
    }

    /**
     * A method to save a new user to the DB.
     * @param user The UserDAO of the new user you want to store in the DB.
     */
    public void saveUser(UserDAO user) {
        System.out.println("SAVING THE NEW USER TO THE DATABASE");
        userRepository.save(user);
    }

    /**
     * A method for finding all users with the same full name.
     * @param firstName The first name of the users.
     * @param lastName The last name of the users.
     * @return Returns a list of UserDAOs containing all users in the DB
     * with the same full name as the one you search for.
     */
    public List<UserDAO> findAllUsersWithSameFullName(String firstName, String lastName) {
        System.out.println("FINDING ALL USERS WITH THE FULLNAME: " + firstName + " " + lastName);
        return userRepository.findUserDAOByFirstNameAndLastName(firstName,lastName);
    }
}
