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
    @Column(name = "user_pos", nullable = false, unique = true)
    private Long userPos;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private UserEntity user;

    @Range(min = 0)
    @ColumnDefault("0")
    private Long score;
}
