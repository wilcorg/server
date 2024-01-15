package org.gogame.server.repositories;

import org.gogame.server.domain.entities.UserLobbyEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserLobbyRepository extends CrudRepository<UserLobbyEntity, Long> {
}
