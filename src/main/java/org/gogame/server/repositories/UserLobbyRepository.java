package org.gogame.server.repositories;

import org.gogame.server.domain.entities.UserLobbyEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserLobbyRepository extends CrudRepository<UserLobbyEntity, Long> {
    UserLobbyEntity findByUserId(Long userId);
}
