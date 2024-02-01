package org.gogame.server.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.gogame.server.domain.entities.enums.GameResult;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "result_score")
public class ResultScoreEntity {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "game_result", length = 5, nullable = false, unique = true)
    private GameResult result;

    @Column(name = "score_value", nullable = false)
    private Long scoreValue;
}
