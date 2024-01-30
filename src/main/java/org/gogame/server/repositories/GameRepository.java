package org.gogame.server.repositories;

import org.gogame.server.domain.entities.GameEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GameRepository extends CrudRepository<GameEntity, Long> {

    @Query(value = """
            SELECT DISTINCT g
            FROM GameEntity g
            WHERE (
                (g.userWhite.userId = :user_a_id)
                OR (g.userBlack.userId = :user_a_id)
            )
            AND g.winner.userId IS NULL
            """)
    Optional<GameEntity> findCurrentGame(@Param("user_a_id") Long userAId);
}
