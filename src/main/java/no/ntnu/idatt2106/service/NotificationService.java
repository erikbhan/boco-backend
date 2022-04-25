package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.NotificationDAO;
import no.ntnu.idatt2106.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public NotificationDAO getNotificationFromNotificationId(int notificationId) {
        return notificationRepository.findNotificationDAOByNotificationID(notificationId);
    }
}
