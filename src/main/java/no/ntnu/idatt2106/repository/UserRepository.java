package no.ntnu.idatt2106.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2106.model.DAO.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    
}
