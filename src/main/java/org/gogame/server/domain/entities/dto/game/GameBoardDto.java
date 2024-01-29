package org.gogame.server.domain.entities.dto.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gogame.server.domain.entities.enums.UserColor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameBoardDto {

    private Integer cellX;

    private Integer cellY;

    private UserColor cellType;
}
