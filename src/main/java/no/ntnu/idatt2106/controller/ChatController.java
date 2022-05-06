package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.middleware.RequireAuth;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.*;
import no.ntnu.idatt2106.service.ChatService;
import no.ntnu.idatt2106.service.UserService;
import no.ntnu.idatt2106.util.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ChatController {
    private SimpMessagingTemplate simpMessagingTemplate;
    private final ChatService chatService;
    private final UserService userService;

    public ChatController(SimpMessagingTemplate simpMessagingTemplate, ChatService chatService, UserService userService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatService = chatService;
        this.userService = userService;
    }

    /**
     * Method to handle websocket connection and broadcast messages
     * @param chatStatusDTO DTO containing the chat status, sender and receiver
     */
    @MessageMapping("/chat")
    public void handleChat(@Payload ChatStatusDTO chatStatusDTO) {
        simpMessagingTemplate.convertAndSendToUser(
                Integer.toString(chatStatusDTO.getTo()), "/queue/messages", chatStatusDTO);
    }

    /**
     * A method to get all chat messages between the active user and the given user
     * @param userId The id of the second user in the conversation
     * @return An array of chatMessageDTOs from the given user
     */
    @GetMapping("/chats/users/{userId}/messages")
    @RequireAuth
    @Operation(summary = "Get all messages in a conversation.", tags = {"Chat"})
    public ChatMessageDTO[] getChatMessages(@PathVariable int userId){
        TokenDTO tokenDTO = TokenUtil.getDataJWT(TokenUtil.getToken());
        return chatService.getConversation(tokenDTO.getAccountId(), userId);
    }

    /**
     * A method to get all the conversations of the active user
     * @return all the conversations the current user is part of
     */
    @GetMapping("/chats/users")
    @RequireAuth
    @Operation(summary = "Get all conversations.", tags = {"Chat"})
    public ConversationDTO[] getAllConversations(){
        TokenDTO tokenDTO = TokenUtil.getDataJWT(TokenUtil.getToken());
        return chatService.getAllConversations(tokenDTO.getAccountId());
    }

    /**
     * A method to send a new message to a given user
     * @param userId The recipient of the message
     * @param newMessageDTO The message to be sent
     */
    @PostMapping("/chats/users/{userId}/messages")
    @RequireAuth
    @ApiResponse(responseCode = "401", description = "Missing authentication access.")
    @Operation(summary = "Creates a new message in a conversation", tags = {"Chat"})
    public void sendMessage(@PathVariable int userId, @RequestBody NewMessageDTO newMessageDTO) throws Exception {
        TokenDTO tokenDTO = TokenUtil.getDataJWT(TokenUtil.getToken());
        UserDAO userDAO = userService.findUserByUserId(userId);
        if(userDAO == null) throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User does not exist");
        chatService.createMessage(
                userDAO,
                userService.findUserByUserId(tokenDTO.getAccountId()),
                newMessageDTO);
    }
}
