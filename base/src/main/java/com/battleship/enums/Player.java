package com.battleship.enums;

public enum Player {
    PLAYER_A, PLAYER_B;

    public Player getOpponent() {
        return this == PLAYER_A ? PLAYER_B : PLAYER_A;
    }
}
