package org.gogame.server.repositories;

import org.gogame.server.domain.entities.GameJournalEntity;
import org.springframework.data.repository.CrudRepository;

public interface GameJournalRepository extends CrudRepository<GameJournalEntity, Long> {
}
