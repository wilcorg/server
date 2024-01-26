package org.gogame.server.domain.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gogame.server.domain.entities.UserLobbyState;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLobbyDto {

    private Long userId;

    private String nickname;

    private Long score;

    private UserLobbyState userState;

    private Boolean isFriend;
}
