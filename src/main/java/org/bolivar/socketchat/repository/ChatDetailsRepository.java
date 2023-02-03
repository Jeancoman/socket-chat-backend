package org.bolivar.socketchat.repository;

import java.util.Optional;

import org.bolivar.socketchat.entity.ChatDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatDetailsRepository extends JpaRepository<ChatDetails, Integer> {
    Optional<ChatDetails> findByChatIdAndUserId(Integer chatId, Integer userId);
}
