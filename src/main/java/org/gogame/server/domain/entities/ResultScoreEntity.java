package org.gogame.server.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "result_score")
public class ResultScoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "result_score_id", nullable = false, unique = true)
    private Long resultScoreId;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_result", length = 5, nullable = false, unique = true)
    private GameResult result;

    @Column(name = "score_value", nullable = false)
    private Long scoreValue;
}
