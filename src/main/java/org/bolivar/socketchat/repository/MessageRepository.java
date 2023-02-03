package org.bolivar.socketchat.repository;

import java.util.Optional;
import java.util.Set;
import org.bolivar.socketchat.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
  Optional<Message> findById(Integer id);
  Set<Message> findByChatIdEquals(Integer id);
  Optional<Message> findFirstByChatIdEqualsOrderByCreatedAtDesc(Integer id);
  Optional<Message> findFirstByChatIdEqualsOrderByIdDesc(Integer id);
  Set<Message> findByChatIdEqualsOrderByCreatedAtAsc(Integer id);
  Set<Message> findByChatIdEqualsOrderByIdAsc(Integer id);

  @Query(
    value = "SELECT COUNT(*) FROM message WHERE message.id > :maxMsgId AND message.chat_id = :chatId",
    nativeQuery = true
  )
  Optional<Integer> countUnreadMessages(Integer maxMsgId, Integer chatId);
}
