package org.gogame.server.domain.entities;

import jakarta.persistence.*;
import lombok.*;

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
}
