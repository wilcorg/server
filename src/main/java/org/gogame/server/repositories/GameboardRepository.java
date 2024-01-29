package org.gogame.server.repositories;

import org.gogame.server.domain.entities.GameboardEntity;
import org.springframework.data.repository.CrudRepository;

public interface GameboardRepository extends CrudRepository<GameboardEntity, Long> {
}
