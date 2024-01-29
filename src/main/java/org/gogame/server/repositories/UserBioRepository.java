package org.gogame.server.repositories;

import org.gogame.server.domain.entities.MessageEntity;
import org.gogame.server.domain.entities.UserBioEntity;
import org.gogame.server.domain.entities.UserFriendInviteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserBioRepository extends CrudRepository<UserBioEntity, Long> {
    @Query(value = """
            SELECT b
            FROM UserBioEntity b
            WHERE b.user.userId = :user_id
            """)
    Optional<UserBioEntity> findByUserId(@Param("user_id") Long userId);
}