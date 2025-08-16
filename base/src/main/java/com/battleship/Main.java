package com.battleship;

public class Main {
    public static void main(String[] args) {
        MainBattleshipGame game = new MainBattleshipGame();
        
        game.initGame(8);
        
        game.addShip("SH1", 2, 1, 1, 5, 1);
        
        game.viewBattleField();
        
        game.startGame();
    }
}