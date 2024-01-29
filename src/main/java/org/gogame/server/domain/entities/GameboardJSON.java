package org.gogame.server.domain.entities;

import lombok.Getter;
import lombok.Setter;
import org.gogame.server.domain.entities.enums.StoneTypeEnum;

import java.util.ArrayList;

public class GameboardJSON {
    @Getter
    @Setter
    private ArrayList<ArrayList<Character>> gameboard;
    @Getter
    private Integer huntedByWhite = 0;
    @Getter
    private Integer huntedByBlack = 0;

    public GameboardJSON() {
        gameboard = new ArrayList<>();
        for (int i = 0; i < 19; i++) {
            gameboard.add(new ArrayList<>());
            for (int j = 0; j < 19; j++) {
                gameboard.get(i).add(' ');
            }
        }
    }

    public void incHuntedByWhite() {
        this.huntedByWhite++;
    }

    public void incHuntedByBlack() {
        this.huntedByBlack++;
    }

    public Character getStone(int x, int y) {
        return gameboard.get(y).get(x);
    }

    public void setStone(int x, int y, StoneTypeEnum stoneType) {
        if (stoneType == StoneTypeEnum.BLACK) {
            gameboard.get(y).set(x, 'B');
        } else if (stoneType == StoneTypeEnum.WHITE) {
            gameboard.get(y).set(x, 'W');
        } else if (stoneType == StoneTypeEnum.EMPTY) {
            gameboard.get(y).set(x, ' ');
        }
    }

}
