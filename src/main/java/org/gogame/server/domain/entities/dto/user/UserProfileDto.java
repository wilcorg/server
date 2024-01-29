package org.gogame.server.domain.entities.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileDto {

    private Long userId;

    private String nickname;

    private Long score;

    private Boolean isFriend;

    private String bio;

    private Float winsPerLosses;
}
