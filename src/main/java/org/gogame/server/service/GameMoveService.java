package org.gogame.server.service;

import lombok.RequiredArgsConstructor;
import org.gogame.server.domain.entities.GameEntity;
import org.gogame.server.domain.entities.dto.game.GameJournalDto;
import org.gogame.server.domain.entities.enums.StoneTypeEnum;
import org.gogame.server.mappers.impl.GameJournalMapper;
import org.gogame.server.repositories.GameJournalRepository;
import org.gogame.server.repositories.GameRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameMoveService {

    private final GameJournalRepository gameJournalRepo;
    private final GameRepository gameRepo;
    private final GameJournalMapper gameJournalMapper;
    private final GameboardService gameboardService;

    public void sendStone(GameJournalDto gameJournalDto, StoneTypeEnum stoneType) {
        try {

            var gameboardJSON = gameboardService.setStone(
                    gameJournalDto.getGameId(),
                    gameJournalDto.getTurnX(),
                    gameJournalDto.getTurnY(),
                    stoneType);

            var gameJournalEntity = gameJournalMapper.mapFrom(gameJournalDto);
            gameJournalEntity.setHuntedByWhite(gameboardJSON.getHuntedByWhite());
            gameJournalEntity.setHuntedByBlack(gameboardJSON.getHuntedByBlack());
            gameJournalRepo.save(gameJournalEntity);

        } catch (NullPointerException | IllegalArgumentException e) {
            throw new NullPointerException("Gameboard not found");
        }
    }

    public void leaveGame(GameJournalDto gameJournalDto) {
        try {
            var gameEntity = gameRepo.findCurrentGame(gameJournalDto.getAuthorId()).orElseThrow();
            if (gameEntity.getUserWhite().getUserId().equals(gameJournalDto.getAuthorId())) {
                gameEntity.setWinner(gameEntity.getUserBlack());
            } else {
                gameEntity.setWinner(gameEntity.getUserWhite());
            }
            gameRepo.save(gameEntity);
            gameJournalRepo.save(gameJournalMapper.mapFrom(gameJournalDto));
        } catch (Exception e) {
            throw new NullPointerException();
        }
    }

    public boolean isOpponentMove(StoneTypeEnum stoneType, GameJournalDto gameJournalDto) {
        var lastGameTurn = gameJournalRepo.findLastGameTurn(gameJournalDto.getGameId());
        if (lastGameTurn.isEmpty()) {
            return stoneType == StoneTypeEnum.WHITE;
        }

        GameEntity gameEntity;
        try {
            gameEntity = gameRepo.findCurrentGame(gameJournalDto.getAuthorId()).orElseThrow();
        } catch (NullPointerException ex) {
            throw new NullPointerException("Game not found");
        }

        // white had moved already
        if (gameEntity.getUserWhite().getUserId().equals(lastGameTurn.get().getAuthor().getUserId())) {
            return stoneType == StoneTypeEnum.BLACK;
        } else {
            return stoneType == StoneTypeEnum.WHITE;
        }

    }
}
