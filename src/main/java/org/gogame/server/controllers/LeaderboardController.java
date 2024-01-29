package org.gogame.server.controllers;

import lombok.RequiredArgsConstructor;
import org.gogame.server.mappers.Mapper;
import org.gogame.server.domain.entities.UserBioEntity;
import org.gogame.server.domain.entities.UserEntity;
import org.gogame.server.domain.entities.dto.user.UserProfileDto;
import org.gogame.server.service.LeaderboardService;
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
import java.util.List;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderboardService leaderboardService;
    private final PermissionValidatorService validatorService;

    @GetMapping("/leaderboard/{id}")
    public ResponseEntity<List<UserProfileDto>> getLeaderboard(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token
    ) {

        if (!validatorService.validateUserId(id, token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<UserProfileDto> leaderboard;
        try {
            leaderboard = leaderboardService.getLeaderboard(id);
        } catch (SQLException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(leaderboard, HttpStatus.OK);
    }
}
