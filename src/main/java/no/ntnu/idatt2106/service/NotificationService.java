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

    public List<NotificationDTO> getCommunityRequestNotifications(int userID){
        List<NotificationDTO> notifications = new ArrayList<>();
        List<CommunityDAO> adminCommunities = userCommunityService.getListOfAllAdminCommunities(userID);
        for (CommunityDAO community : adminCommunities){
            List<CommunityRequestDAO> requests = communityRequestService.getRequestsByCommunity(community);
            for (CommunityRequestDAO request : requests){
                NotificationDAO dao = notificationRepository.findNotificationDAOByCommunityRequestDAO(request);
                NotificationDTO dto = new NotificationDTO(false, dao.getCreatedTime(), dao.getCommunityRequestDAO().getCommunityRequestID());
                notifications.add(dto);
            }
        }
        return notifications;
    }

    public int getNumberOfUnreadMessages(int userID){
        return chatService.getUnreadMessages(userID);
    }

    public List<ChatMessageDTO> getLastChatMessageFromDistinctSender(int userID){
        return chatService.getLastReceivedMessagesFromDistinctSenders(userID);
    }

    public void saveCommunityRequestNotification(NotificationDAO notificationDAO) {

    }

    public void saveChatMessageNotification(NotificationDAO notificationDAO) {

    }

    public NotificationDAO turnDTOIntoDAO(NotificationDTO notificationDTO) {
        NotificationDAO notificationDAO = new NotificationDAO();
        notificationDAO.setChatMessageDAO(chatService.getById(notificationDTO.getChatMessageID()));
        notificationDAO.setCommunityRequestDAO(communityRequestService.getById(notificationDTO.getCommunityRequestID()));
        notificationDAO.setSeen(notificationDTO.isSeen());
        notificationDAO.setCreatedTime(notificationDTO.getCreatedTime());
        return notificationDAO;
    }
}
