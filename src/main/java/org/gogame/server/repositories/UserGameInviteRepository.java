package org.gogame.server.repositories;

import org.gogame.server.domain.entities.UserGameInviteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserGameInviteRepository extends CrudRepository<UserGameInviteEntity, Long> {
    @Query(value = """
            SELECT u
            FROM UserGameInviteEntity u
            WHERE u.userSender.userId = :sender_id
            AND u.userReceiver.userId = :receiver_id
            """)
    List<UserGameInviteEntity> findByUserIds(@Param("sender_id") Long senderId, @Param("receiver_id") Long receiverId);

    @Query(value = """
            SELECT u
            FROM UserGameInviteEntity u
            WHERE u.userSender.userId = :user_id
            OR u.userReceiver.userId = :user_id
            """)
    List<UserGameInviteEntity> findBySingleUserId(@Param("user_id") Long userId);
}
