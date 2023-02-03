package org.bolivar.socketchat.controller;

import java.util.Set;
import org.bolivar.socketchat.entity.Chat;
import org.bolivar.socketchat.entity.Chat.Type;
import org.bolivar.socketchat.entity.Message;
import org.bolivar.socketchat.entity.User;
import org.bolivar.socketchat.service.ChatService;
import org.bolivar.socketchat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/chats", produces = "application/json")
@CrossOrigin(origins = "http://localhost:5173")
public class ChatController {

  @Autowired
  private ChatService chatService;

  @Autowired
  private MessageService messageService;

  @Autowired
  private SimpMessagingTemplate template;

  @GetMapping("/{id}")
  public Chat getChat(@PathVariable("id") Integer id) {
    return chatService.getChat(id);
  }

  @GetMapping("/{id}/users")
  public Set<User> allUsers(@PathVariable("id") Integer id) {
    return chatService.getUsers(id);
  }

  @GetMapping("/{id}/messages")
  public ResponseEntity<Object> allMessages(@PathVariable("id") Integer id) {
    Set<Message> messages = messageService.getMessages(id);

    if (messages == null) return new ResponseEntity<>(
      "There are no messages in this chat.",
      HttpStatus.NOT_FOUND
    );

    return ResponseEntity.ok(messages);
  }

  @GetMapping(path = "/{id}/messages", params = "last=1")
  public ResponseEntity<Message> lastMessage(@PathVariable("id") Integer id) {
    Message message = messageService.getLastMessage(id);

    if (message == null) return new ResponseEntity<>(
      null,
      HttpStatus.NOT_FOUND
    );

    return ResponseEntity.ok(message);
  }

  @PostMapping("/{id}/messages")
  @ResponseStatus(HttpStatus.CREATED)
  public Message sendMessage(
    @PathVariable("id") Integer id,
    @RequestBody Message message
  ) {
    Message savedMessage = messageService.saveMessage(message);
    template.convertAndSend("/queue/chat/" + id, savedMessage);
    return savedMessage;
  }

  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Chat patchChat(
    @PathVariable("id") Integer id,
    @RequestBody Chat chat
  ) {
    return chatService.updateChat(id, chat);
  }

  @GetMapping(params = { "userId", "type" })
  public Chat getByMembersAndType(
    @RequestParam("userId") Set<Integer> userId,
    @RequestParam("type") Type type
  ) {
    return chatService.getByUsersAndType(userId, type);
  }
}
