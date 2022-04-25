package no.ntnu.idatt2106.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2106.model.DAO.NotificationDAO;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationDAO, Integer> {
    NotificationDAO findNotificationDAOByNotificationID(int notificationId);
}
