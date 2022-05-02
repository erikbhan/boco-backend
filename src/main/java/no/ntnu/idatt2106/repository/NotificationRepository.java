package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.DAO.CommunityRequestDAO;
import no.ntnu.idatt2106.model.DTO.NotificationDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2106.model.DAO.NotificationDAO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A class meant to access the notification table in the DB.
 * This class contains some premade methods and some custom-made ones.
 */
@Repository
public interface NotificationRepository extends JpaRepository<NotificationDAO, Integer> {
    NotificationDAO findNotificationDAOByNotificationID(int notificationId);
    NotificationDAO findNotificationDAOByCommunityRequestDAO(CommunityRequestDAO request);
}
