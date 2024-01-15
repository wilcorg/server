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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "turn_id_seq")
    @SequenceGenerator(name = "turn_id_seq", allocationSize = 1)
    @Column(nullable = false, unique = true)
    private Long turn_id;

    @OneToOne
    @JoinColumn(name = "game_id", referencedColumnName = "game_id")
    private GameEntity game;

    @Enumerated(EnumType.STRING)
    @Column(length = 9, nullable = false)
    private GameActionType actionType;

    @Range(min = 0, max = 18)
    private Integer turn_x;

    @Range(min = 0, max = 18)
    private Integer turn_y;

    @Column(nullable = false)
    private Timestamp turn_date;
}
