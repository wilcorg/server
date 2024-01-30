package org.gogame.server.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.gogame.server.domain.entities.enums.GameState;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "game")
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "game_id", nullable = false, unique = true)
    private Long gameId;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private UserEntity userWhite;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private UserEntity userBlack;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private UserEntity winner;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "game_state", length = 16, nullable = false)
    private GameState state = GameState.PLAYING;
}
