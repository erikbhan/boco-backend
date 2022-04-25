package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.ChatMessageDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.ChatMessageDTO;
import no.ntnu.idatt2106.model.DTO.NewMessageDTO;
import no.ntnu.idatt2106.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.sql.Date;

@Service
public class ChatService {

    private final ChatMessageRepository _chatMessageRepository;

    public ChatService(ChatMessageRepository chatMessageRepository) {
        _chatMessageRepository = chatMessageRepository;
    }


    public ChatMessageDTO[] getConversation(int accountId, int userId) {
        return Arrays.stream(_chatMessageRepository.getConversation(accountId, userId))
            .map(ChatMessageDTO::new)
            .toArray(ChatMessageDTO[]::new);
    }

    public void getAllConversations(int parseInt) {
        /*
            _chatMessageRepository.getAllConversations(parseInt);

         */
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
