package org.gogame.server.controllers;

import lombok.RequiredArgsConstructor;
import org.gogame.server.domain.entities.GameEntity;
import org.gogame.server.domain.entities.dto.game.GameDto;
import org.gogame.server.domain.entities.dto.user.UserInviteDto;
import org.gogame.server.domain.entities.dto.user.UserProfileDto;
import org.gogame.server.service.GameService;
import org.gogame.server.service.PermissionValidatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService service;

    private final PermissionValidatorService validatorService;

    @PostMapping("/invite/send")
    public ResponseEntity<UserInviteDto> sendGameInvite (
            @RequestBody UserInviteDto request,
            @RequestHeader("Authorization") String token
    ) {
        if (!validatorService.validateUserId(request.getUserSenderId(), token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        UserInviteDto response;
        try {
            response = service.sendGameInvite(request);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/invite/accept")
    public ResponseEntity<UserInviteDto> acceptGameInvite (
            @RequestBody UserInviteDto request,
            @RequestHeader("Authorization") String token
    ) {
        if (!validatorService.validateUserId(request.getUserReceiverId(), token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        UserInviteDto response;
        try {
            response = service.acceptGameInvite(request);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/invite/reject")
    public ResponseEntity<UserInviteDto> rejectGameInvite (
            @RequestBody UserInviteDto request,
            @RequestHeader("Authorization") String token
    ) {
        if (!validatorService.validateUserId(request.getUserReceiverId(), token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        UserInviteDto response;
        try {
            response = service.rejectGameInvite(request);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/invite/fetch/{id}")
    public ResponseEntity<List<UserInviteDto>> fetchGameInvite (
            @PathVariable Long id,
            @RequestHeader("Authorization") String token
    ) {
        if (!validatorService.validateUserId(id, token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<UserInviteDto> response;
        try {
            response = service.fetchGameInvite(id);
            if (response.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/current/{id}")
    public ResponseEntity<GameEntity> getCurrentGame (
            @PathVariable Long id,
            @RequestHeader("Authorization") String token
    ) {
        if (!validatorService.validateUserId(id, token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        GameEntity response = service.getCurrentGame(id);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{gameId}/winner/{userId}")
    public ResponseEntity<?> setGameWinner (
            @PathVariable Long gameId,
            @PathVariable Long winnerId,
            @RequestBody UserProfileDto myProfile,
            @RequestHeader("Authorization") String token
    ) {
        if (!validatorService.validateUserId(myProfile.getUserId(), token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            service.setGameWinner(gameId, winnerId);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
