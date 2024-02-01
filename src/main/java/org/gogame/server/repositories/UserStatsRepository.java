package org.gogame.server.repositories;

import org.gogame.server.domain.entities.UserEntity;
import org.gogame.server.domain.entities.UserStatsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserStatsRepository extends CrudRepository<UserStatsEntity, Long> {

    @Query(value = """
            SELECT u
            FROM UserStatsEntity u
            WHERE u.user = :user
            """)
    Optional<UserStatsEntity> findByUserId(UserEntity user);
}