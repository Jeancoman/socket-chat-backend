package org.bolivar.socketchat.service;

import java.util.Optional;
import java.util.Set;
import org.bolivar.socketchat.entity.Message;
import org.bolivar.socketchat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

  @Autowired
  private MessageRepository repository;

  public Set<Message> getMessages(Integer id) {
    Set<Message> messages = repository.findByChatIdEqualsOrderByIdAsc(id);

    if(messages.isEmpty()) return null;

    return messages;
  }

  public Message getLastMessage(Integer id) {
    Optional<Message> message = repository.findFirstByChatIdEqualsOrderByIdDesc(id);
    
    if(message.isEmpty()) return null;

    return message.get();
  }

  public Message saveMessage(Message message) {
    return repository.save(message);
  }

  public Integer getUnreadMessages(Integer maxMsgId, Integer chatId){
    Optional<Integer> count = repository.countUnreadMessages(maxMsgId, chatId);

    if(count.isEmpty()) return 0;

    return count.get();
  }
}
