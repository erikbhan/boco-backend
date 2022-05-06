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
    private final RentRepository rentRepository;

    public ChatService(ChatMessageRepository chatMessageRepository, UserRepository userRepository, RentRepository rentRepository) {
        _chatMessageRepository = chatMessageRepository;
        _userRepository = userRepository;
        this.rentRepository = rentRepository;
    }

    /**
     * Finds the chat message with the given id
     * @param id the id of the chat message to search for
     * @return The chat message to search for
     */
    public ChatMessageDAO getById(int id){
        return _chatMessageRepository.findByMessageID(id);
    }

    /**
     * Gets all messages between to users
     * @param accountId The active user id
     * @param userId The id of the other user
     * @return An array of all messages sent between the users
     */
    public ChatMessageDTO[] getConversation(int accountId, int userId) {
        return Arrays.stream(_chatMessageRepository.getConversation(accountId, userId))
            .map(ChatMessageDTO::new)
            .toArray(ChatMessageDTO[]::new);
    }

    /**
     * Retrieves all conversations that a given user has taken part in
     * @param accountId The id of the user we want to find the conversations for
     * @return An array of all the conversations
     */
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

        List<RentDAO> rentalRequests = rentRepository.findAllByListing_User_UserID(accountId);
        for (RentDAO rentalRequest : rentalRequests) {
            boolean found = false;
            for (ConversationDTO conversation : conversations) {
                if (conversation.getRecipient().getUserId() == rentalRequest.getRenter().getUserID()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                ChatMessageDTO message = new ChatMessageDTO(-1, "Rental Request", rentalRequest.getCreatedAt(), accountId, rentalRequest.getRenter().getUserID());
                ConversationDTO conversation = new ConversationDTO(message, new PublicUserDTO(_userRepository.getById(rentalRequest.getRenter().getUserID())));
                conversations = Stream.concat(Arrays.stream(conversations), Stream.of(conversation)).toArray(ConversationDTO[]::new);
            }
        }

        // Sort so newest conversation is first
        Arrays.sort(conversations, (o1, o2) -> Long.compare(o2.getLastMessage().getTimestamp(), o1.getLastMessage().getTimestamp()));
        return conversations;
    }

    /**
     * Creates and saves a new message to the database
     * @param sender The sender of the new message
     * @param receiver The receiver of the new message
     * @param newMessageDTO The DTO of the message to be saved
     */
    public void createMessage(UserDAO sender, UserDAO receiver, NewMessageDTO newMessageDTO) {
        no.ntnu.idatt2106.model.DAO.ChatMessageDAO chatMessageDAO = new no.ntnu.idatt2106.model.DAO.ChatMessageDAO();
        chatMessageDAO.setSendingUser(sender);
        chatMessageDAO.setReceivingUser(receiver);
        chatMessageDAO.setText(newMessageDTO.getMessage());
        chatMessageDAO.setTimeSent(System.currentTimeMillis());
        _chatMessageRepository.save(chatMessageDAO);
    }

    /**
     * Finds the number of unread messages the given user has
     * @param userID The id of the user we want to find unread messages for
     * @return The number of unread messages
     */
    public int getUnreadMessages(int userID){
        UserDAO user = _userRepository.findUserDAOByUserID(userID);
        return _chatMessageRepository.findByReceivingUserAndIsReadFalse(user).size();
    }

    /**
     * Finds the last chat message from every conversation the user has taken part in
     * @param userID The user we want to find messages for
     * @return A list of the last chat messages from all conversations
     */
    public List<ChatMessageDTO> getLastReceivedMessagesFromDistinctSenders(int userID){
        List<ChatMessageDAO> lastMessages = _chatMessageRepository.getLastReceivedFromDistinct(userID);
        List<ChatMessageDTO> dtos = new ArrayList<>();
        for (ChatMessageDAO messageDAO : lastMessages){
            dtos.add(new ChatMessageDTO(messageDAO));
        }
        return dtos;
    }
}
