package org.gogame.server.domain.entities;

import org.gogame.server.domain.entities.enums.StoneTypeEnum;

import java.util.ArrayList;

public class GameboardJSON {
    private ArrayList<ArrayList<Character>> gameboard;
    public GameboardJSON() {
        gameboard = new ArrayList<>();
        for (int i = 0; i < 19; i++) {
            gameboard.add(new ArrayList<>());
            for (int j = 0; j < 19; j++) {
                gameboard.get(i).add(' ');
            }
        }
    }


    // used for jackson ObjectMapper
    @SuppressWarnings("unused")
    public void setGameboard(ArrayList<ArrayList<Character>> gameboard) {
        this.gameboard = gameboard;
    }

    // used for jackson ObjectMapper
    @SuppressWarnings("unused")
    public ArrayList<ArrayList<Character>> getGameboard() {
        return gameboard;
    }

    public Character getStone(int x, int y) {
        return gameboard.get(y).get(x);
    }

    public void setStone(int x, int y, StoneTypeEnum stoneType) {
        if (stoneType == StoneTypeEnum.BLACK) {
            gameboard.get(y).set(x, 'B');
        } else if (stoneType == StoneTypeEnum.WHITE) {
            gameboard.get(y).set(x, 'W');
        } else {
            gameboard.get(y).set(x, ' ');
        }
    }
}
