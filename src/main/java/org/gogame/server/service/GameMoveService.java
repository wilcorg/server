package org.gogame.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.gogame.server.domain.entities.GameboardEntity;
import org.gogame.server.domain.entities.GameboardJSON;
import org.gogame.server.domain.entities.dto.game.GameJournalDto;
import org.gogame.server.domain.entities.enums.StoneTypeEnum;
import org.gogame.server.mappers.impl.GameJournalMapper;
import org.gogame.server.repositories.GameJournalRepository;
import org.gogame.server.repositories.GameRepository;
import org.gogame.server.repositories.GameboardRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameMoveService {

    private final GameJournalRepository gameJournalRepo;
    private final GameRepository gameRepo;
    private final GameboardRepository gameboardRepo;
    private final ObjectMapper objectMapper;
    private final GameJournalMapper gameJournalMapper;

    public void sendStone(GameJournalDto gameJournalDto, StoneTypeEnum stoneType) throws JsonProcessingException {
        try {

            GameboardEntity gameboardEntity = gameboardRepo.findById(gameJournalDto.getGameId()).orElseThrow();
            GameboardJSON gameboardJSON = objectMapper.readValue(gameboardEntity.getGameboard(), GameboardJSON.class);
            if (gameboardJSON.getStone(gameJournalDto.getTurnX(), gameJournalDto.getTurnY()) != ' ') {
                throw new IllegalArgumentException("Cell is not empty");
            }

            gameboardJSON.setStone(gameJournalDto.getTurnX(), gameJournalDto.getTurnY(), stoneType);

            gameboardEntity.setGameboard(objectMapper.writeValueAsString(gameboardJSON));
            gameboardRepo.save(gameboardEntity);
            gameJournalRepo.save(gameJournalMapper.mapFrom(gameJournalDto));

        } catch (NullPointerException e) {
            throw new NullPointerException("Gameboard not found");
        }
    }

    public boolean isOpponentMove(StoneTypeEnum stoneType, GameJournalDto gameJournalDto) {
        var lastGameTurn = gameJournalRepo.findLastGameTurn(gameJournalDto.getGameId());
        if (lastGameTurn == null) {
            return stoneType == StoneTypeEnum.WHITE;
        }

        var gameEntity = gameRepo.findCurrentGame(gameJournalDto.getAuthorId());

        // white had moved already
        if (gameEntity.getUserWhite().getUserId().equals(lastGameTurn.getAuthor().getUserId())) {
            return stoneType == StoneTypeEnum.BLACK;
        } else {
            return stoneType == StoneTypeEnum.WHITE;
    }
}
}
