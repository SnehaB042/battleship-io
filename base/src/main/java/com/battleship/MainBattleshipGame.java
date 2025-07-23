package com.battleship;

import com.battleship.exceptions.GameException;

public class MainBattleshipGame {
    private final GameService gameService;
    
    public MainBattleshipGame() {
        this.gameService = new GameService();
    }
    
    public void initGame(int n) {
        try {
            gameService.initGame(n);
        } catch (GameException e) {
            System.err.println("Error initializing game: " + e.getMessage());
        }
    }
    
    public void addShip(String id, int size, int xA, int yA, int xB, int yB) {
        try {
            gameService.addShip(id, size, xA, yA, xB, yB);
        } catch (GameException e) {
            System.err.println("Error adding ship: " + e.getMessage());
        }
    }
    
    // public void startGame() {
    //     try {
    //         gameService.startGame();
    //     } catch (GameException e) {
    //         System.err.println("Error starting game: " + e.getMessage());
    //     }
    // }
    
    public void viewBattleField() {
        try {
            gameService.viewBattleField();
        } catch (GameException e) {
            System.err.println("Error viewing battlefield: " + e.getMessage());
        }
    }    
}
