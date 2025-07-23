package com.battleship;

public class Main {
    public static void main(String[] args) {
        MainBattleshipGame game = new MainBattleshipGame();
        
        game.initGame(6);

        // game.addShip("SH1", 2, 1, 1, 2, 2);
        
        game.addShip("SH1", 2, 1, 5, 4, 4);
        // game.addShip("SH2", 1, 0, 0, 5, 0);
        
        game.viewBattleField();
        
        // game.startGame();
    }
}