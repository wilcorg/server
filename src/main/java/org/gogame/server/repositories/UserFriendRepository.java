package org.gogame.server.repositories;

import org.gogame.server.domain.entities.UserFriendEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserFriendRepository extends CrudRepository<UserFriendEntity, Long> {
}
