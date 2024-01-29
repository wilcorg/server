package org.gogame.server.domain.entities.dto.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameMoveDto {

    private Long userId;

    private Long gameId;

    private Integer turnX;

    private Integer turnY;
}
