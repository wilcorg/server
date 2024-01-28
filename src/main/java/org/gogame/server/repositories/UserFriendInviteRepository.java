package org.gogame.server.repositories;

import org.gogame.server.domain.entities.UserFriendInviteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserFriendInviteRepository extends CrudRepository<UserFriendInviteEntity, Long> {
    @Query(value = """
            SELECT u
            FROM UserFriendInviteEntity u
            WHERE u.userSender.userId = :userSenderId
            AND u.userReceiver.userId = :userReceiverId
            """)
    List<UserFriendInviteEntity> findDuplicatingInvites(@Param("userSenderId") Long userSenderId, @Param("userReceiverId") Long userReceiverId);

    @Query(value = """
            SELECT u
            FROM UserFriendInviteEntity u
            WHERE u.userReceiver.userId = :userId
            AND u.status = 'PENDING'
    """)
    List<UserFriendInviteEntity> findAllPendingInvites(@Param("userId") Long userReceiverId);

    @Query(value = """
            SELECT u
            FROM UserFriendInviteEntity u
            WHERE u.userSender.userId = :userSenderId
            AND u.userReceiver.userId = :userReceiverId
            AND u.status = 'PENDING'
            """)
    Optional<UserFriendInviteEntity> findOnePendingInvite(@Param("userSenderId") Long userSenderId, @Param("userReceiverId") Long userReceiverId);
}
