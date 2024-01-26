package org.gogame.server.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "game_journal")
public class GameJournalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "turn_id", nullable = false, unique = true)
    private Long turnId;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private GameEntity game;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private UserEntity author;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_action", length = 9, nullable = false)
    private GameAction action;

    @Range(min = 0, max = 18)
    @Column(name = "turn_x")
    private Integer turnX;

    @Range(min = 0, max = 18)
    @Column(name = "turn_y")
    private Integer turnY;

    @Column(name = "turn_date", nullable = false)
    private Timestamp turnDate;
}
