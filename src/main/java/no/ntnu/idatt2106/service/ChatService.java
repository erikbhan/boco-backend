package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.ChatMessageDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.*;
import no.ntnu.idatt2106.repository.ChatMessageRepository;
import no.ntnu.idatt2106.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;

@Service
public class ChatService {

    private final ChatMessageRepository _chatMessageRepository;
    private final UserRepository _userRepository;

    public ChatService(ChatMessageRepository chatMessageRepository, UserRepository userRepository) {
        _chatMessageRepository = chatMessageRepository;
        _userRepository = userRepository;
    }


    public ChatMessageDTO[] getConversation(int accountId, int userId) {
        return Arrays.stream(_chatMessageRepository.getConversation(accountId, userId))
            .map(ChatMessageDTO::new)
            .toArray(ChatMessageDTO[]::new);
    }

    public ConversationDTO[] getAllConversations(int accountId) {
        int[] list = _chatMessageRepository.getConversationUsers(accountId);
        UserDAO[] users = _userRepository.findAllByUserIds(list);
        ConversationDTO[] conversations = new ConversationDTO[users.length];
        // Get last message for each user
        for (int i = 0; i < users.length; i++) {
            UserDAO user = users[i];
            ChatMessageDAO chatMessageDAO = _chatMessageRepository.getLastMessage(accountId, user.getUserID());
            ChatMessageDTO chatMessageDTO = new ChatMessageDTO(chatMessageDAO);
            conversations[i] = new ConversationDTO(chatMessageDTO, new PublicUserDTO(user));
        }

        return conversations;
    }

    public void createMessage(UserDAO sender, UserDAO receiver, NewMessageDTO newMessageDTO) {
        ChatMessageDAO chatMessageDAO = new ChatMessageDAO();
        chatMessageDAO.setSendingUserID(sender);
        chatMessageDAO.setReceivingUserID(receiver);
        chatMessageDAO.setText(newMessageDTO.getMessage());
        chatMessageDAO.setTimeSent(new Date(System.currentTimeMillis()));
        _chatMessageRepository.save(chatMessageDAO);
    }
}
