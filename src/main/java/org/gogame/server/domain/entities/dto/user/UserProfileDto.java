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

    @Builder.Default
    private Long score = 0L;

    @Builder.Default
    private Boolean isFriend = false;

    private String bio;

    @Builder.Default
    private Float winsPerLosses = 0.0f;
}
