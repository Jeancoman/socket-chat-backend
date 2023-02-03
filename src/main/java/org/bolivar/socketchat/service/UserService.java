package org.bolivar.socketchat.service;

import java.util.Optional;
import java.util.Set;
import org.bolivar.socketchat.entity.Chat;
import org.bolivar.socketchat.entity.User;
import org.bolivar.socketchat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository repository;

  public Set<User> getFriends(int id) {
    Optional<User> user = repository.findById(id);
    Set<User> friends = user.get().getFriends();
    Set<User> friendsOf = user.get().getFriendOf();
    friends.addAll(friendsOf);

    return friends;
  }

  public Set<Chat> geChats(int id) {
    Optional<User> user = repository.findById(id);
    return user.get().getChats();
  }

  public User getUser(int id){
    Optional<User> user = repository.findById(id);
    return user.get();
  }
}
