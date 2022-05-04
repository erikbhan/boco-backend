package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.ChatMessageDAO;
import no.ntnu.idatt2106.model.DAO.RentDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.*;
import no.ntnu.idatt2106.repository.ChatMessageRepository;
import no.ntnu.idatt2106.repository.RentRepository;
import no.ntnu.idatt2106.repository.UserRepository;
import no.ntnu.idatt2106.util.TokenUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ChatService {

    private final ChatMessageRepository _chatMessageRepository;
    private final UserRepository _userRepository;
    private final UserService _userService;
    private final RentRepository rentRepository;

    public ChatService(ChatMessageRepository chatMessageRepository, UserRepository userRepository, UserService userService, RentRepository rentRepository) {
        _chatMessageRepository = chatMessageRepository;
        _userRepository = userRepository;
        _userService = userService;
        this.rentRepository = rentRepository;
    }

    public ChatMessageDAO getById(int id){
        return _chatMessageRepository.findByMessageID(id);
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
            no.ntnu.idatt2106.model.DAO.ChatMessageDAO chatMessageDAO = _chatMessageRepository.getLastMessage(accountId, user.getUserID());
            ChatMessageDTO chatMessageDTO = new ChatMessageDTO(chatMessageDAO);
            conversations[i] = new ConversationDTO(chatMessageDTO, new PublicUserDTO(user));
        }

        return conversations;
    }

    public void createMessage(UserDAO sender, UserDAO receiver, NewMessageDTO newMessageDTO) {
        no.ntnu.idatt2106.model.DAO.ChatMessageDAO chatMessageDAO = new no.ntnu.idatt2106.model.DAO.ChatMessageDAO();
        chatMessageDAO.setSendingUser(sender);
        chatMessageDAO.setReceivingUser(receiver);
        chatMessageDAO.setText(newMessageDTO.getMessage());
        chatMessageDAO.setTimeSent(System.currentTimeMillis());
        _chatMessageRepository.save(chatMessageDAO);
    }

    public int getUnreadMessages(int userID){
        UserDAO user = _userRepository.findUserDAOByUserID(userID);
        return _chatMessageRepository.findByReceivingUserAndIsReadFalse(user).size();
    }

    public List<ChatMessageDTO> getLastReceivedMessagesFromDistinctSenders(int userID){
        List<ChatMessageDAO> lastMessages = _chatMessageRepository.getLastReceivedFromDistinct(userID);
        List<ChatMessageDTO> dtos = new ArrayList<>();
        for (ChatMessageDAO messageDAO : lastMessages){
            dtos.add(new ChatMessageDTO(messageDAO));
        }
        return dtos;
    }
}
