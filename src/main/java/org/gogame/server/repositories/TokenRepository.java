package org.gogame.server.repositories;

import org.gogame.server.domain.entities.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    @Query(value = """
            SELECT t
            FROM TokenEntity t
            INNER JOIN UserEntity u
            ON t.user.userId = u.userId
            WHERE u.userId = :userId
            AND t.revoked = false
            AND t.expired = false
            """)
    List<TokenEntity> findAllValidTokensByUser(Long userId);

    Optional<TokenEntity> findByToken(String token);
}