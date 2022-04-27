package no.ntnu.idatt2106.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2106.model.DAO.ChatMessageDAO;
import org.springframework.data.jpa.repository.Query;

public interface ChatMessageRepository extends JpaRepository<ChatMessageDAO, Integer> {
    @Query(value = "SELECT * FROM chat_message c WHERE c.sending_user_id = ?1 OR c.receiving_user_id = ?2 OR  c.receiving_user_id = ?1 OR c.sending_user_id = ?2 ORDER BY c.time_sent ASC", nativeQuery = true)
    ChatMessageDAO[] getConversation(int accountId, int userId);

    @Query(value = "SELECT * FROM chat_message c WHERE c.sending_user_id = ?1 OR c.receiving_user_id = ?2 OR  c.receiving_user_id = ?1 OR c.sending_user_id = ?2 ORDER BY c.time_sent ASC limit 1", nativeQuery = true)
    ChatMessageDAO getLastMessage(int accountId, int userId);


    @Query(value = " SELECT DISTINCT userid FROM (SELECT DISTINCT sending_user_id AS userid FROM chat_message cm WHERE cm.receiving_user_id = 1 UNION SELECT DISTINCT receiving_user_id AS userid FROM chat_message cmd WHERE cmd.sending_user_id = 1) AS joinedList", nativeQuery = true)
    int[] getConversationUsers(int accountId);


}
