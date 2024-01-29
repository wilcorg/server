package org.gogame.server.service;

import lombok.RequiredArgsConstructor;
import org.gogame.server.domain.entities.GameboardJSON;
import org.gogame.server.domain.entities.enums.StoneTypeEnum;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MoveLogicService {

    public boolean checkIfFriendStoneIsChoking(GameboardJSON gameboardJSON, int x, int y, StoneTypeEnum stoneType) {
        return friendNeighbourChoking(gameboardJSON, x, up(y), stoneType) &&
                friendNeighbourChoking(gameboardJSON, right(x), y, stoneType) &&
                friendNeighbourChoking(gameboardJSON, x, down(y), stoneType) &&
                friendNeighbourChoking(gameboardJSON, left(x), y, stoneType);
    }

    public GameboardJSON checkIfStoneCanChokeEnemy(GameboardJSON gameboardJSON, StoneTypeEnum stoneType) {
        Character enemy = stoneType == StoneTypeEnum.WHITE ? 'B' : 'W';

        for (int search_y = 0; search_y < 19; search_y++) {
            for (int search_x = 0; search_x < 19; search_x++) {
                if (gameboardJSON.getStone(search_x, search_y).equals(enemy)) {
                    if (enemyNeighbourChoking(gameboardJSON, search_x, up(search_y), stoneType)
                            && enemyNeighbourChoking(gameboardJSON, right(search_x), search_y, stoneType)
                            && enemyNeighbourChoking(gameboardJSON, search_x, down(search_y), stoneType)
                            && enemyNeighbourChoking(gameboardJSON, left(search_x), search_y, stoneType)) {

                       gameboardJSON.setStone(search_x, search_y, StoneTypeEnum.EMPTY);
                       if (stoneType == StoneTypeEnum.WHITE) {
                           gameboardJSON.incHuntedByWhite();
                       } else if(stoneType == StoneTypeEnum.BLACK) {
                           gameboardJSON.incHuntedByBlack();
                        }
                    }
                }
            }
        }
        return gameboardJSON;
    }

    private boolean enemyNeighbourChoking(GameboardJSON gameboardJSON, Integer neigh_x, Integer neigh_y, StoneTypeEnum enemy) {
        if (neigh_x == null || neigh_y == null) {
            return true;
        }
        return gameboardJSON.getStone(neigh_x, neigh_y).equals('W') && enemy == StoneTypeEnum.WHITE
                || gameboardJSON.getStone(neigh_x, neigh_y).equals('B') && enemy == StoneTypeEnum.BLACK;
    }

    private boolean friendNeighbourChoking(GameboardJSON gameboardJSON, Integer neigh_x, Integer neigh_y, StoneTypeEnum friend) {
        if (neigh_x == null || neigh_y == null) {
            return true;
        }
        return gameboardJSON.getStone(neigh_x, neigh_y).equals('W') && friend == StoneTypeEnum.WHITE
                || gameboardJSON.getStone(neigh_x, neigh_y).equals('B') && friend == StoneTypeEnum.BLACK;
    }

    public Integer up(int y) {
        if (y == 0) return null;
        else return y - 1;
    }

    public Integer down(int y) {
        if (y == 18) return null;
        else return y + 1;
    }

    public Integer left(int x) {
        if (x == 0) return null;
        else return x - 1;
    }

    public Integer right(int x) {
        if (x == 18) return null;
        else return x + 1;
    }
}
