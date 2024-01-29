package org.gogame.server.repositories;

import org.gogame.server.domain.entities.GameJournalEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface GameJournalRepository extends CrudRepository<GameJournalEntity, Long> {
    @Query(value = """
            SELECT j
            FROM GameJournalEntity j
            WHERE j.game.gameId = :gameId
            ORDER BY j.turnId DESC
            LIMIT 1
            """)
    GameJournalEntity findLastGameTurn(@Param("gameId") Long gameId);
}
