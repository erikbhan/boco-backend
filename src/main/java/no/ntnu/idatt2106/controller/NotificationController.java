package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.middleware.RequireAuth;
import no.ntnu.idatt2106.model.DAO.NotificationDAO;
import no.ntnu.idatt2106.model.DTO.ChatMessageDTO;
import no.ntnu.idatt2106.model.DTO.NotificationDTO;
import no.ntnu.idatt2106.model.DTO.TokenDTO;
import no.ntnu.idatt2106.service.ChatService;
import no.ntnu.idatt2106.service.CommunityRequestService;
import no.ntnu.idatt2106.service.NotificationService;
import no.ntnu.idatt2106.util.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequireAuth
@ApiResponse(responseCode = "200", description = "OK")
public class NotificationController {

    private final NotificationService notificationService;
    private final CommunityRequestService communityRequestService;
    private final ChatService chatService;

    public NotificationController(NotificationService notificationService, CommunityRequestService communityRequestService, ChatService chatService) {
        this.notificationService = notificationService;
        this.communityRequestService = communityRequestService;
        this.chatService = chatService;
    }


    /**
     * Adds a community join request notification to the database.
     * @param notificationDTO Notification DTO for adding to the database.
     */
    @Operation(summary = "Adds community join request notification")
    @ApiResponse(responseCode = "201", description = "CREATED")
    @ApiResponse(responseCode = "400", description = "CommunityRequest not found")
    @ApiResponse(responseCode = "400", description = "Could not add notification")
    @ApiResponse(responseCode = "401", description = "UNAUTHORIZED")
    @PostMapping("notifications/communities/joinrequest")
    public void addCommunityRequestNotification(@RequestBody NotificationDTO notificationDTO) throws StatusCodeException {
        NotificationDAO notificationDAO = notificationService.turnDTOIntoDAO(notificationDTO);
        try{
            TokenDTO userToken = TokenUtil.getDataJWT();
            int tokenUserId = userToken.getAccountId();
        } catch (Exception e) {
            throw new StatusCodeException(HttpStatus.UNAUTHORIZED, "Token not found");
        }
        if(communityRequestService.getById(notificationDTO.getCommunityRequestID()) == null){
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Could not find community join request with that id.");
        }
        try {
            notificationService.saveCommunityRequestNotification(notificationDAO);
        } catch (Exception e) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Couldn't add community request notification");
        }
        throw new StatusCodeException(HttpStatus.CREATED, "Community join request notification added");
    }

    /**
     * Gets all the community request notifications for the user that is logged in when the get call is made.
     */
    @Operation(summary = "Gets community join request notifications for the authenticated user")
    @ApiResponse(responseCode = "401", description = "UNAUTHORIZED")
    @GetMapping("notifications/communities/joinrequests")
    public List<NotificationDTO> getCommunityRequestNotification() throws StatusCodeException {
        int tokenUserId;
        try{
            TokenDTO userToken = TokenUtil.getDataJWT();
            tokenUserId = userToken.getAccountId();
        } catch (Exception e) {
            throw new StatusCodeException(HttpStatus.UNAUTHORIZED, "Token not found");
        }
        try {
            return notificationService.getCommunityRequestNotifications(tokenUserId);
        } catch (Exception e) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Couldn't get community join request notification");
        }
    }

    /**
     * Adds a chat message notification to the database.
     * @param notificationDTO Notification DTO for adding to the database.
     */
    @Operation(summary = "Adds chat message notification")
    @ApiResponse(responseCode = "201", description = "CREATED")
    @ApiResponse(responseCode = "400", description = "ChatMessage not found")
    @ApiResponse(responseCode = "400", description = "Could not add notification")
    @ApiResponse(responseCode = "401", description = "UNAUTHORIZED")
    @PostMapping("notifications/chat")
    public void addChatMessageNotification(@RequestBody NotificationDTO notificationDTO) throws StatusCodeException {
        NotificationDAO notificationDAO = notificationService.turnDTOIntoDAO(notificationDTO);
        try{
            TokenDTO userToken = TokenUtil.getDataJWT();
            int tokenUserId = userToken.getAccountId();
        } catch (Exception e) {
            throw new StatusCodeException(HttpStatus.UNAUTHORIZED, "Token not found");
        }
        if(chatService.getById(notificationDTO.getChatMessageID()) == null){
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Could not find chatMessage with that id.");
        }
        try {
            notificationService.saveChatMessageNotification(notificationDAO);
        } catch (Exception e) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Couldn't add chat message notification");
        }
        throw new StatusCodeException(HttpStatus.CREATED, "Chat message notification added");
    }

    /**
     * Gets lastest chat message for the user from each message sender.
     * @return
     * @throws StatusCodeException
     */
    @Operation(summary = "Gets latest chat message notification from each sender to the authenticated user")
    @ApiResponse(responseCode = "401", description = "UNAUTHORIZED")
    @GetMapping("notifications/chat")
    public List<ChatMessageDTO> getLastChatMessageNotificationFromEachSender() throws StatusCodeException {
        int tokenUserId;
        try{
            TokenDTO userToken = TokenUtil.getDataJWT();
            tokenUserId = userToken.getAccountId();
        } catch (Exception e) {
            throw new StatusCodeException(HttpStatus.UNAUTHORIZED, "Token not found");
        }
        try {
            return notificationService.getLastChatMessageFromDistinctSender(tokenUserId);
        } catch (Exception e) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Couldn't get chat message notifications");
        }
    }

    /**
     * @return number of unread messages for a user.
     * @throws StatusCodeException When token is not found in header.
     */
    @Operation(summary = "Gets number of unread messages for a user")
    @GetMapping("notifications/chat/amount")
    @ApiResponse(responseCode = "401", description = "UNAUTHORIZED")
    public int getNumberOfUnreadMessages() throws StatusCodeException {
        int tokenUserId;
        try{
            TokenDTO userToken = TokenUtil.getDataJWT();
            tokenUserId = userToken.getAccountId();
        } catch (Exception e) {
            throw new StatusCodeException(HttpStatus.UNAUTHORIZED, "Token not found");
        }
        return notificationService.getNumberOfUnreadMessages(tokenUserId);
    }

    /**
     * Sets a notification as seen in the database.
     * @param notificationID ID of notification to be set as seen.
     */
    @Operation(summary = "Sets a notification as seen")
    @ApiResponse(responseCode = "401", description = "UNAUTHORIZED")
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST")
    @PatchMapping("notification/{notificationID}/seen")
    public void setNotificationAsSeen(@PathVariable int notificationID) throws StatusCodeException {
        int tokenUserId;
        NotificationDAO notificationDAO;

        //Checks if the person making the request is logged in.
        try{
            TokenDTO userToken = TokenUtil.getDataJWT();
            tokenUserId = userToken.getAccountId();
        } catch (Exception e) {
            throw new StatusCodeException(HttpStatus.UNAUTHORIZED, "Token not found");
        }

        //Checks if the notification ID exists in the database.
        try {
            notificationDAO = notificationService.getNotificationFromNotificationId(notificationID);
        } catch (Exception e) {
            throw new StatusCodeException(HttpStatus.BAD_REQUEST, "Notification ID not found");
        }

        //Checks if the user who is making the request is the owner of the notification.
        if (notificationDAO.getChatMessage().getReceivingUser().getUserID() == tokenUserId ||
                notificationDAO.getCommunityRequest().getUser().getUserID() == tokenUserId) {
            notificationService.getNotificationFromNotificationId(notificationID).setSeen(true);
        } else {
            throw new StatusCodeException(HttpStatus.UNAUTHORIZED, "User not authorized to change this notification");
        }

    }
}
