package org.gogame.server.repositories;

import org.gogame.server.domain.entities.UserStatsEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserStatsRepository extends CrudRepository<UserStatsEntity, Long> {
}