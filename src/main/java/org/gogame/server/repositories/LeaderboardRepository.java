package org.gogame.server.repositories;

import org.gogame.server.domain.entities.LeaderboardEntity;
import org.springframework.data.repository.CrudRepository;

public interface LeaderboardRepository extends CrudRepository<LeaderboardEntity, Long> {
}
