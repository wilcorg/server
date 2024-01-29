package org.gogame.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gogame.server.domain.entities.GameboardEntity;
import org.gogame.server.domain.entities.GameboardJSON;
import org.gogame.server.domain.entities.enums.StoneTypeEnum;
import org.gogame.server.repositories.GameboardRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameboardService {

    private final GameboardRepository gameboardRepo;
    private final ObjectMapper objectMapper;
    private final MoveLogicService moveLogicService;

    private GameboardJSON readGameboard(Long gameId) {
        try {
            GameboardEntity gameboardEntity = gameboardRepo.findById(gameId).orElseThrow();
            return objectMapper.readValue(gameboardEntity.getGameboard(), GameboardJSON.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void saveGameboard(Long gameId, GameboardJSON gameboardJSON) {
        try {
            GameboardEntity gameboardEntity = gameboardRepo.findById(gameId).orElseThrow();
            gameboardEntity.setGameboard(objectMapper.writeValueAsString(gameboardJSON));
            gameboardRepo.save(gameboardEntity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public GameboardJSON setStone(Long gameId, int x, int y, StoneTypeEnum stoneType) {
        if (getStone(gameId, x, y) != ' ') {
            throw new IllegalArgumentException("Cell is not empty");
        }
        GameboardJSON tempGameboardJSON = readGameboard(gameId);
        tempGameboardJSON.setStone(x, y, stoneType);

        if (!moveLogicService.checkIfFriendStoneIsChoking(tempGameboardJSON, x, y, stoneType)) {
            saveGameboard(gameId, tempGameboardJSON);
        } else {
            throw new IllegalArgumentException("");
        }

        tempGameboardJSON = moveLogicService.checkIfStoneCanChokeEnemy(tempGameboardJSON, stoneType);
        saveGameboard(gameId, tempGameboardJSON);
        return tempGameboardJSON;
    }

    public Character getStone(Long gameId, int x, int y) {
        GameboardJSON gameboardJSON = readGameboard(gameId);
        return gameboardJSON.getStone(x, y);
    }
}
