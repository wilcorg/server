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
public class GameJournalDto {

    private Long gameId;

    private Long authorId;

    private Integer turnX;

    private Integer turnY;

    private GameAction action;
}
