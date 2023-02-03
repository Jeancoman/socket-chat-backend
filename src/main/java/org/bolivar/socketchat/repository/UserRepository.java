package org.bolivar.socketchat.repository;

import java.util.Optional;
import org.bolivar.socketchat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findById(int id);
}
