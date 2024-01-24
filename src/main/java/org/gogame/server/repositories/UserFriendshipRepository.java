package org.gogame.server.repositories;

import org.gogame.server.domain.entities.UserFriendshipEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserFriendshipRepository extends CrudRepository<UserFriendshipEntity, Long> {
}
