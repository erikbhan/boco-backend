package no.ntnu.idatt2106.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2106.model.DAO.ChatMessageDAO;
import org.springframework.data.jpa.repository.Query;

public interface ChatMessageRepository extends JpaRepository<ChatMessageDAO, Integer> {
    @Query(value = "SELECT * FROM chat_message c WHERE c.sending_user_id = ?1 OR c.receiving_user_id = ?2 OR  c.receiving_user_id = ?1 OR c.sending_user_id = ?2 ORDER BY c.time_sent ASC", nativeQuery = true)
    ChatMessageDAO[] getConversation(int accountId, int userId);
}
