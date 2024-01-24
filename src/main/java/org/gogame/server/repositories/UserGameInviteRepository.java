package org.gogame.server.repositories;

import org.gogame.server.domain.entities.UserGameInviteEntity;
import org.springframework.data.repository.CrudRepository;
public interface UserGameInviteRepository extends CrudRepository<UserGameInviteEntity, Long> {
}
