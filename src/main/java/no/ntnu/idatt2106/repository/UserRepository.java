package no.ntnu.idatt2106.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2106.model.DAO.UserDAO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A class meant to access the user table in the DB.
 * This class contains some premade methods and some custom-made ones.
 */
@Repository
public interface UserRepository extends JpaRepository<UserDAO, Integer> {
    UserDAO findUserDAOByEmail(String email);

    UserDAO findUserDAOByUserID(int userId);

    List<UserDAO> findUserDAOByFirstNameAndLastName(String firstName, String lastName);

    @Query(value = "SELECT * FROM public.user WHERE user_id IN ?1", nativeQuery = true)
    UserDAO[] findAllByUserIds(int[] userids);
}
