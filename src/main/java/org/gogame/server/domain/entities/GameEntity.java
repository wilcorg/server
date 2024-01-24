package org.gogame.server.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "game")
@IdClass(GameEntity.GameEntityId.class)
public class GameEntity {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GameEntityId implements Serializable {
        private Long gameId;
        private Long userId;
    }

    public GameEntityId getId() {
        return new GameEntityId(this.getGameId(), this.getUserId());
    }

    @Id
    // TODO figure out id generation for this
    @Column(name = "game_id", nullable = false)
    private Long gameId;

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_color", nullable = false)
    private UserColor userColor;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_result", length = 5)
    private GameResult result;
}
