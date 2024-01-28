package org.gogame.server.controllers;

import lombok.RequiredArgsConstructor;
import org.gogame.server.mappers.Mapper;
import org.gogame.server.domain.entities.UserBioEntity;
import org.gogame.server.domain.entities.UserEntity;
import org.gogame.server.domain.entities.dto.UserProfileDto;
import org.gogame.server.service.UserBioService;
import org.gogame.server.service.UserProfileService;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;


@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final UserBioService userBioService;

    private final Mapper<Pair<UserEntity, UserBioEntity>, UserProfileDto> userProfileMapper;

    @GetMapping("/user/profile/{id}")
    public ResponseEntity<UserProfileDto> getUserInfo(@PathVariable Long id) {

        UserEntity userInfo;
        try {
            userInfo = userProfileService.getUserInfo(id);
        } catch (SQLException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserBioEntity userBio;
        try {
            userBio = userBioService.getUserBio(id);
        } catch (SQLException ex) {
            userBio = UserBioEntity.builder()
                    .userId(id)
                    .bio("")
                    .build();
        }

        var userProfile = Pair.of(userInfo, userBio);

        return new ResponseEntity<>(userProfileMapper.mapTo(userProfile), HttpStatus.OK);
    }
}