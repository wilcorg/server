package org.gogame.server.controllers;

import lombok.RequiredArgsConstructor;
import org.gogame.server.mappers.Mapper;
import org.gogame.server.domain.entities.UserBioEntity;
import org.gogame.server.domain.entities.UserEntity;
import org.gogame.server.domain.entities.dto.UserProfileDto;
import org.gogame.server.service.PermissionValidatorService;
import org.gogame.server.service.UserBioService;
import org.gogame.server.service.UserProfileService;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final UserBioService userBioService;
    private final PermissionValidatorService validatorService;

    private final Mapper<Pair<UserEntity, UserBioEntity>, UserProfileDto> userProfileMapper;

    @GetMapping("/user/profile")
    public ResponseEntity<UserProfileDto> getUserInfo(@RequestParam Long id_author,
                                                      @RequestParam Long id_about,
                                                      @RequestHeader("Authorization") String token) {


        if (!validatorService.validateUserId(id_author, token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        UserEntity userInfo;
        try {
            userInfo = userProfileService.getUserInfo(id_about);
        } catch (SQLException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserBioEntity userBio;
        try {
            userBio = userBioService.getUserBio(id_about);
        } catch (SQLException ex) {
            userBio = UserBioEntity.builder()
                    .userId(id_about)
                    .bio("")
                    .build();
        }

        var userProfile = Pair.of(userInfo, userBio);

        return new ResponseEntity<>(userProfileMapper.mapTo(userProfile), HttpStatus.OK);
    }
}