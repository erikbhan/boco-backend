package no.ntnu.idatt2106.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2106.model.DAO.NotificationDAO;
import org.springframework.stereotype.Repository;

/**
 * A class meant to access the notification table in the DB.
 * This class contains some premade methods and some custom-made ones.
 */
@Repository
public interface NotificationRepository extends JpaRepository<NotificationDAO, Integer> {
    NotificationDAO findNotificationDAOByNotificationID(int notificationId);
}
