package no.ntnu.idatt2106.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2106.model.DAO.ChatMessageDAO;
import org.springframework.data.jpa.repository.Query;

public interface ChatMessageRepository extends JpaRepository<ChatMessageDAO, Integer> {
    @Query(value = "SELECT c FROM ChatMessageDAO c WHERE c.receivingUserID = ?1 OR c.receivingUserID = ?2 OR  c.sendingUserID = ?1 OR c.sendingUserID = ?2 ORDER BY c.timeSent ASC", nativeQuery = true)
    ChatMessageDAO[] getConversation(int accountId, int userId);
}
