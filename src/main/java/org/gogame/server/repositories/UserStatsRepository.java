package org.gogame.server.repositories;

import org.gogame.server.domain.entities.UserStatsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserStatsRepository extends CrudRepository<UserStatsEntity, Long> {

    @Query(value = """
            SELECT u
            FROM UserStatsEntity u
            WHERE u.user.userId = :user_id
            """)
    UserStatsEntity findByUserId(Long userId);
}