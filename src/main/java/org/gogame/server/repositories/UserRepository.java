package org.gogame.server.repositories;

import org.gogame.server.domain.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByNickname(String nickname);
}
