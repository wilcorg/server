package org.gogame.server.repositories;

import org.gogame.server.domain.entities.UserAvatarEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserAvatarRepository extends CrudRepository<UserAvatarEntity, Long> {
}
