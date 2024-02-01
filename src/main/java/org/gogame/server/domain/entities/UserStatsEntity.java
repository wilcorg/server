package org.gogame.server.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_stats")
public class UserStatsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_stats_id", nullable = false, unique = true)
    private Long userStatsId;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private UserEntity user;

    @Range(min = 0)
    @ColumnDefault("0")
    @Column(name = "game_played")
    private Long gamePlayed;

    @Range(min = 0)
    @ColumnDefault("0")
    @Column(name = "game_won")
    private Long gameWon;
}
