package org.gogame.server.domain.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gogame.server.domain.entities.GameAction;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameActionDto {

    private Long userId;

    private Long gameId;

    private GameAction action;
}
