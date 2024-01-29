package org.gogame.server.controllers;

import lombok.RequiredArgsConstructor;
import org.gogame.server.domain.entities.GameEntity;
import org.gogame.server.domain.entities.GameJournalEntity;
import org.gogame.server.domain.entities.dto.game.GameJournalDto;
import org.gogame.server.domain.entities.dto.user.UserProfileDto;
import org.gogame.server.domain.entities.enums.GameAction;
import org.gogame.server.domain.entities.enums.StoneTypeEnum;
import org.gogame.server.mappers.impl.GameJournalMapper;
import org.gogame.server.repositories.GameJournalRepository;
import org.gogame.server.repositories.GameRepository;
import org.gogame.server.repositories.UserRepository;
import org.gogame.server.service.GameMoveService;
import org.gogame.server.service.GameService;
import org.gogame.server.service.JwtService;
import org.gogame.server.service.PermissionValidatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/v1/game")
@RequiredArgsConstructor
public class GameController {

    private final GameMoveService gameMoveService;
    private final PermissionValidatorService validatorService;
    private final JwtService jwtService;
    private final GameRepository gameRepo;
    private final UserRepository userRepo;
    private final GameJournalRepository gameJournalRepo;
    private final GameJournalMapper gameJournalMapper;
    private final GameService gameService;

    @PostMapping("/turn/send")
    public ResponseEntity<GameJournalDto> sendStone(
            @RequestBody GameJournalDto request,
            @RequestHeader("Authorization") String token
    ) {
        if (!validatorService.validateGameId(request.getGameId(), token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        GameEntity gameEntity = gameRepo.findCurrentGame(request.getAuthorId());

        StoneTypeEnum stoneType;
        if (request.getAuthorId().equals(gameEntity.getUserWhite().getUserId())) {
            stoneType = StoneTypeEnum.WHITE;
        } else {
            stoneType = StoneTypeEnum.BLACK;
        }

        if (!gameMoveService.isOpponentMove(stoneType, request)) {
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }

        GameAction requestAction = request.getAction();
        switch (requestAction) {
            case MOVE -> {
                try {
                    gameMoveService.sendStone(request, stoneType);
                } catch (Exception e) {
                    System.err.println("Error while parsing JSON\n");
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            }
            case LEAVE -> {
                try {
                    gameMoveService.leaveGame(request);
                } catch (Exception e) {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/turn/fetch")
    public ResponseEntity<GameJournalDto> fetchLastMove(
            @RequestHeader("Authorization") String token
    ) {
        String username = jwtService.extractUsername(token.substring(7));
        try {
            Long userId = userRepo.findByNickname(username).orElseThrow().getUserId();
            Long gameId = gameRepo.findCurrentGame(userId).getGameId();
            GameJournalEntity lastTurn = gameJournalRepo.findLastGameTurn(gameId);
            return new ResponseEntity<>(gameJournalMapper.mapTo(lastTurn), HttpStatus.OK);
        } catch (NullPointerException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/current/{id}")
    public ResponseEntity<GameEntity> getCurrentGame (
            @PathVariable Long id,
            @RequestHeader("Authorization") String token
    ) {
        if (!validatorService.validateUserId(id, token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        GameEntity response = gameService.getCurrentGame(id);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{gameId}/setwinner/{winnerId}")
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
            gameService.setGameWinner(gameId, winnerId);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
