package org.gogame.server.repositories;

import org.gogame.server.domain.entities.GameEntity;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<GameEntity, GameEntity.GameEntityId> {
}
