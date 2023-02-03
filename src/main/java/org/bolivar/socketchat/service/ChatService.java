package org.bolivar.socketchat.service;

import java.util.Optional;
import java.util.Set;
import org.bolivar.socketchat.entity.Chat;
import org.bolivar.socketchat.entity.Message;
import org.bolivar.socketchat.entity.User;
import org.bolivar.socketchat.entity.Chat.Type;
import org.bolivar.socketchat.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

  @Autowired
  private ChatRepository repository;

  public Set<User> getUsers(int id) {
    Optional<Chat> chat = repository.findById(id);
    return chat.get().getUsers();
  }

  public Set<Message> getMessages(int id) {
    Optional<Chat> chat = repository.findById(id);
    return chat.get().getMessages();
  }

  public Chat getChat(int id) {
    Optional<Chat> chat = repository.findById(id);
    return chat.get();
  }

  public Chat updateChat(int id, Chat newChat) {
    Chat chat = repository.findById(id).get();

    if (newChat.getName() != null) {
      chat.setName(newChat.getName());
    }

    if (newChat.getDescription() != null) {
      chat.setDescription(newChat.getDescription());
    }

    if (newChat.getIsActive() != null) {
      chat.setIsActive(newChat.getIsActive());
    }

    if (newChat.getChatImageUrl() != null) {
      chat.setChatImageUrl(newChat.getChatImageUrl());
    }

    return repository.save(chat);
  }

  public Chat getByUsersAndType(Set<Integer> userId, Type type){
    return repository.findByUsersIdInAndTypeIs(userId, type).get();
  }
}
