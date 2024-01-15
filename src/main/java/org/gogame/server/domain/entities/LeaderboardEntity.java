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
    @Column(nullable = false, unique = true, name = "userPos")
    private Long userPos;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity userId;

    @Range(min = 0)
    @ColumnDefault("0")
    private Long score;
}
