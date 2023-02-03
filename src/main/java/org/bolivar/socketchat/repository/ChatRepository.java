package org.bolivar.socketchat.repository;

import java.util.Optional;
import java.util.Set;
import org.bolivar.socketchat.entity.Chat;
import org.bolivar.socketchat.entity.Chat.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
  Optional<Chat> findById(Integer id);

  @Query(
    value = "SELECT c FROM Chat c JOIN c.users u WHERE u.id IN (:userId) AND c.type = :type GROUP BY c.id HAVING COUNT(*) = 2"
  )
  Optional<Chat> findByUsersIdInAndTypeIs(Set<Integer> userId, Type type);
}
