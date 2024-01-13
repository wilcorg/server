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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_stats_id_seq")
    @SequenceGenerator(name = "user_stats_id_seq", allocationSize = 1)
    @Column(nullable = false, unique = true)
    private Long user_stats_id;

    @Range(min = 0)
    @ColumnDefault("0")
    private Long game_played;

    @Range(min = 0)
    @ColumnDefault("0")
    private Long game_won;

    @Range(min = 0)
    @ColumnDefault("0")
    private Long game_lost;
}
