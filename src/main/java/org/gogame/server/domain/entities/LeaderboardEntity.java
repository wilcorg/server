package org.gogame.server.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "leaderboard")
public class LeaderboardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "leaderboard_stats_id", nullable = false, unique = true)
    private Long leaderboardId;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private UserEntity user;

    @Builder.Default
    @Range(min = 0)
    @ColumnDefault("0")
    private Long score = 0L;
}
