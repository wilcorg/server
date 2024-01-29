package org.gogame.server.domain.entities.dto.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gogame.server.domain.entities.enums.GameAction;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameActionDto {

    private Long userId;

    private Long gameId;

    private GameAction action;
}
