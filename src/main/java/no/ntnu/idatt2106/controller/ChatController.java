package no.ntnu.idatt2106.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.middleware.RequireAuth;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.ChatMessageDTO;
import no.ntnu.idatt2106.model.DTO.ChatStatusDTO;
import no.ntnu.idatt2106.model.DTO.NewMessageDTO;
import no.ntnu.idatt2106.model.DTO.TokenDTO;
import no.ntnu.idatt2106.service.ChatService;
import no.ntnu.idatt2106.service.UserService;
import no.ntnu.idatt2106.util.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
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
     * @param chatStatusDTO
     */
    @MessageMapping("/chat")
    public void handleChat(@Payload ChatStatusDTO chatStatusDTO) {
        simpMessagingTemplate.convertAndSendToUser(
                Integer.toString(chatStatusDTO.getTo()), "/queue/messages", chatStatusDTO);
    }

    @GetMapping("/chats/users/{userId}/messages")
    @RequireAuth
    @ApiResponse(responseCode = "200", description = "Returns all messages in a conversation.")
    @ApiResponse(responseCode = "401", description = "Unauthorized access.")
    @Operation(summary = "Get all messages in a conversation.", tags = {"Chat"})
    public ChatMessageDTO[] getChatMessages(@PathVariable int userId) throws Exception {
        TokenDTO tokenDTO = TokenUtil.getDataJWT(TokenUtil.getToken());
        return chatService.getConversation(Integer.parseInt(tokenDTO.getAccountId()), userId);
    }

    @GetMapping("/chats/users")
    @RequireAuth
    @ApiResponse(responseCode = "200", description = "Returns all conversations.")
    @ApiResponse(responseCode = "401", description = "Unauthorized access.")
    @Operation(summary = "Get all conversations.", tags = {"Chat"})
    public void getAllConversations() throws Exception {
        TokenDTO tokenDTO = TokenUtil.getDataJWT(TokenUtil.getToken());
        chatService.getAllConversations(Integer.parseInt(tokenDTO.getAccountId()));
    }

    @PostMapping("/chats/users/{userId}/messages")
    @RequireAuth
    @ApiResponse(responseCode = "200", description = "Create a new message.")
    @ApiResponse(responseCode = "401", description = "Missing authentication access.")
    @Operation(summary = "Creates a new message in a conversation", tags = {"Chat"})
    public void sendMessage(@PathVariable int userId, @RequestBody NewMessageDTO newMessageDTO) throws Exception {
        TokenDTO tokenDTO = TokenUtil.getDataJWT(TokenUtil.getToken(););

        UserDAO userDAO = userService.findUserByUserId(userId);
        if(userDAO == null) throw new StatusCodeException(HttpStatus.BAD_REQUEST, "User does not exist");

        chatService.createMessage(
                userDAO,
                userService.findUserByUserId(Integer.parseInt(tokenDTO.getAccountId())),
                newMessageDTO);
    }
}
