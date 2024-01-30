package org.gogame.server.domain.entities.dto.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gogame.server.domain.entities.enums.GameState;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameDto {

    private Long gameId;

    private Long userWhiteId;

    private Long userBlackId;

    private GameState state;
}
