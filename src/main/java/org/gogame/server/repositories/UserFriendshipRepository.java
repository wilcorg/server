package org.gogame.server.repositories;

import org.gogame.server.domain.entities.UserFriendshipEntity;
import org.gogame.server.domain.entities.UserGameInviteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserFriendshipRepository extends CrudRepository<UserFriendshipEntity, Long> {
    @Query(value = """
            SELECT DISTINCT f
            FROM UserFriendshipEntity f
            WHERE (
                (f.userA.userId = :user_a_id AND f.userB.userId = :user_b_id)
                OR (f.userA.userId = :user_b_id AND f.userB.userId = :user_a_id)
            )
            """)
    Optional<UserFriendshipEntity> findByUserIds(@Param("user_a_id") Long userAId, @Param("user_b_id") Long userBId);

    @Query(value = """
            SELECT f
            FROM UserFriendshipEntity f
            WHERE f.userA.userId = :user_id
            OR f.userB.userId = :user_id
            """)
    List<UserFriendshipEntity> findBySingleUserId(@Param("user_id") Long userId);
}
