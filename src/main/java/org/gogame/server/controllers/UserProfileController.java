package org.gogame.server.controllers;

import lombok.RequiredArgsConstructor;
import org.gogame.server.mappers.Mapper;
import org.gogame.server.domain.entities.UserBioEntity;
import org.gogame.server.domain.entities.UserEntity;
import org.gogame.server.domain.entities.dto.user.UserProfileDto;
import org.gogame.server.service.PermissionValidatorService;
import org.gogame.server.service.UserBioService;
import org.gogame.server.service.UserProfileService;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<UserProfileDto> getUserInfo(@RequestParam Long idAuthor,
                                                      @RequestParam Long idAbout,
                                                      @RequestHeader("Authorization") String token) {


        if (!validatorService.validateUserId(idAuthor, token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        UserEntity userInfo;
        try {
            userInfo = userProfileService.getUserInfo(idAbout);
        } catch (SQLException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserBioEntity userBio;
        try {
            userBio = userBioService.getUserBio(idAbout);
        } catch (SQLException ex) {
            userBio = UserBioEntity.builder()
                    .userId(idAbout)
                    .bio("")
                    .build();

        }

        var userProfile = Pair.of(userInfo, userBio);

        return new ResponseEntity<>(userProfileMapper.mapTo(userProfile), HttpStatus.OK);
    }
}
