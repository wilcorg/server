package org.gogame.server.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "gameboard")
public class GameboardEntity {

    @Id
    private Long gameId;

    @Column(name = "gameboard", length = 2048, nullable = false)  // with huge overhead
    private String gameboard;
}
