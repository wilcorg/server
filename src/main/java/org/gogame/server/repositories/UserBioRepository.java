package org.gogame.server.repositories;

import org.gogame.server.domain.entities.MessageEntity;
import org.gogame.server.domain.entities.UserBioEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserBioRepository extends CrudRepository<UserBioEntity, Long> {
}