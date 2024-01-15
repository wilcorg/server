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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "result_score_id_seq")
    @SequenceGenerator(name = "result_score_id_seq", allocationSize = 1)
    @Column(nullable = false, unique = true, name = "result_score_id")
    private Long resultScoreId;

    @Column(length = 5, nullable = false)
    private String result;

    @Column(nullable = false, name = "score_value")
    private Long scoreValue;
}
