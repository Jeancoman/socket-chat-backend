package org.bolivar.socketchat.controller;

import java.util.Set;
import org.bolivar.socketchat.entity.Chat;
import org.bolivar.socketchat.entity.ChatDetails;
import org.bolivar.socketchat.entity.User;
import org.bolivar.socketchat.service.ChatDetailsService;
import org.bolivar.socketchat.service.MessageService;
import org.bolivar.socketchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/users", produces = "application/json")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

  @Autowired
  private UserService service;

  @Autowired
  private MessageService messageService;

  @Autowired
  private ChatDetailsService detailsService;

  @GetMapping("/{id}")
  public User getUser(@PathVariable("id") int id) {
    return service.getUser(id);
  }

  @GetMapping("/{id}/friends")
  public Set<User> getFriends(@PathVariable("id") int id) {
    return service.getFriends(id);
  }

  @GetMapping("/{id}/chats")
  public Set<Chat> getChats(@PathVariable("id") int id) {
    return service.geChats(id);
  }

  @GetMapping("/{userId}/chats/{chatId}/details")
  public ChatDetails getChatDetails(
    @PathVariable("userId") Integer userId,
    @PathVariable("chatId") Integer chatId
  ) {
    return detailsService.getByChatAndUser(chatId, userId);
  }

  @PatchMapping("/{userId}/chats/{chatId}/details")
  public ChatDetails patchChatDetails(
    @PathVariable("userId") Integer userId,
    @PathVariable("chatId") Integer chatId,
    @RequestBody ChatDetails newDetails
  ) {
    return detailsService.updateChatDetails(newDetails, chatId, userId);
  }

  @GetMapping("/{userId}/chats/{chatId}/messages/unread")
  public Integer getUnreadMessages(
    @PathVariable("userId") Integer userId,
    @PathVariable("chatId") Integer chatId
  ) {
    ChatDetails details = detailsService.getByChatAndUser(chatId, userId);
    if (details == null) return 0;
    return messageService.getUnreadMessages(details.getMaxMessageId(), chatId);
  }
}
