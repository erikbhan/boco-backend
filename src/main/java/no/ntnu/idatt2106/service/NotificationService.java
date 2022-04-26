package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.NotificationDAO;
import no.ntnu.idatt2106.repository.NotificationRepository;
import org.springframework.stereotype.Service;

/**
 * This class is uses the access-point to the notification table in the DB.
 * This class uses the methods from {@link no.ntnu.idatt2106.repository.NotificationRepository NotificationRepository}
 */
@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    /**
     * A method to get a notification dao from a notification id.
     * @param notificationId The id of the notification.
     * @return Returns the notification dao matching the given id.
     */
    public NotificationDAO getNotificationFromNotificationId(int notificationId) {
        return notificationRepository.findNotificationDAOByNotificationID(notificationId);
    }
}
