package com.battleship;

import com.battleship.draft.Battlefield;
import com.battleship.draft.Ship;
import com.battleship.enums.Player;
import com.battleship.exceptions.GameException;
import com.battleship.models.Coordinate;

public class GameService {
    private final Battlefield battlefield;
    private Player currentPlayer;
    private boolean gameInitialized;
    private boolean gameStarted;

    public GameService() {
        this.battlefield = new Battlefield();
        this.gameInitialized = false;
        this.gameStarted = false;
    }

    public void initGame(int n) throws GameException {
        if (n <= 0 || n % 2 != 0) {
            throw new GameException("Board size must be a positive even number");
        }
        battlefield.initialize(n);
        gameInitialized = true;        
        System.out.println(String.format("Game initialized on a board of size : %d x %d", n, n));
    }

    public void addShip(String id, int size, int xA, int yA, int xB, int yB) throws GameException {
        if (!gameInitialized) {
            throw new GameException("Game not initialized");
        }
        
        if (gameStarted) {
            throw new GameException("Cannot add ships after game has started");
        }
        
        if (size <= 0) {
            throw new GameException("Ship size must be positive");
        }
        
        if (id == null || id.trim().isEmpty()) {
            throw new GameException("Ship ID cannot be null or empty");
        }
        
        // Add ship for PlayerA
        Ship shipA = new Ship(id, size, new Coordinate(xA, yA), Player.PLAYER_A);
        battlefield.addShip(shipA);
        
        // Add ship for PlayerB
        Ship shipB = new Ship(id, size, new Coordinate(xB, yB), Player.PLAYER_B);
        battlefield.addShip(shipB);
        
        System.out.println("Ship " + id + " added for both players");
    }


    
}
