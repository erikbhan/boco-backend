package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;
import no.ntnu.idatt2106.model.DAO.CommunityRequestDAO;
import no.ntnu.idatt2106.model.DAO.NotificationDAO;
import no.ntnu.idatt2106.model.DTO.ChatMessageDTO;
import no.ntnu.idatt2106.model.DTO.NotificationDTO;
import no.ntnu.idatt2106.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is uses the access-point to the notification table in the DB.
 * This class uses the methods from {@link no.ntnu.idatt2106.repository.NotificationRepository NotificationRepository}
 */
@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserCommunityService userCommunityService;
    private final CommunityRequestService communityRequestService;
    private final ChatService chatService;

    public NotificationService(NotificationRepository notificationRepository, UserCommunityService userCommunityService, CommunityRequestService communityRequestService, ChatService chatService) {
        this.notificationRepository = notificationRepository;
        this.userCommunityService = userCommunityService;
        this.communityRequestService = communityRequestService;
        this.chatService = chatService;
    }

    /**
     * A method to get a notification dao from a notification id.
     * @param notificationId The id of the notification.
     * @return Returns the notification dao matching the given id.
     */
    public NotificationDAO getNotificationFromNotificationId(int notificationId) {
        return notificationRepository.findNotificationDAOByNotificationID(notificationId);
    }

    /**
     * Gets all community request notifications
     * @param userID userID to find notifications for
     * @return A list of NotificationDTOs classified as community requests
     */
    public List<NotificationDTO> getCommunityRequestNotifications(int userID) {
        List<NotificationDTO> notifications = new ArrayList<>();
        List<CommunityDAO> adminCommunities = userCommunityService.getListOfAllAdminCommunities(userID);
        for (CommunityDAO community : adminCommunities) {
            List<CommunityRequestDAO> requests = communityRequestService.getRequestsByCommunity(community);
            for (CommunityRequestDAO request : requests) {
                NotificationDAO dao = notificationRepository.findNotificationDAOByCommunityRequest(request);
                NotificationDTO dto = new NotificationDTO(false, dao.getCreatedTime(), dao.getCommunityRequest().getCommunityRequestID());
                notifications.add(dto);
            }
        }
        return notifications;
    }

    /**
     * Gets the amount of unread messages for a user
     * @param userID user to find messages for
     * @return Integer containing the amount of messages that are unread
     */
    public int getNumberOfUnreadMessages(int userID) {
        return chatService.getUnreadMessages(userID);
    }

    /**
     * Gets last chat message from every unique sender
     * @param userID user to find messages for
     * @return A list of ChatMessageDTOs
     */
    public List<ChatMessageDTO> getLastChatMessageFromDistinctSender(int userID) {
        return chatService.getLastReceivedMessagesFromDistinctSenders(userID);
    }

    /**
     * Saves a community request notification
     * @param notificationDAO NotificationDAO to save
     */
    public void saveCommunityRequestNotification(NotificationDAO notificationDAO) {
        notificationRepository.save(notificationDAO);
    }

    /**
     * Saves a chat request notification
     * @param notificationDAO NotificationDAO to save
     */
    public void saveChatMessageNotification(NotificationDAO notificationDAO) {
        notificationRepository.save(notificationDAO);
    }

    /**
     * Turns a NotificationDTO into a NotificationDAO
     * @param notificationDTO DTO to turn into DAO
     * @return NotificationDAO containing the information from the NotificationDTO
     */
    public NotificationDAO turnDTOIntoDAO(NotificationDTO notificationDTO) {
        NotificationDAO notificationDAO = new NotificationDAO();
        notificationDAO.setChatMessage(chatService.getById(notificationDTO.getChatMessageID()));
        notificationDAO.setCommunityRequest(communityRequestService.getById(notificationDTO.getCommunityRequestID()));
        notificationDAO.setSeen(notificationDTO.isSeen());
        notificationDAO.setCreatedTime(notificationDTO.getCreatedTime());
        return notificationDAO;
    }
}
