package com.battleship;

import com.battleship.enums.Player;
import com.battleship.exceptions.GameException;
import com.battleship.models.Battlefield;
import com.battleship.models.Coordinate;
import com.battleship.models.Ship;

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

        try{
            Ship shipA = new Ship(id, size, new Coordinate(xA, yA), Player.PLAYER_A);
            battlefield.addShip(shipA);
        
            Ship shipB = new Ship(id, size, new Coordinate(xB, yB), Player.PLAYER_B);
            battlefield.addShip(shipB);

            System.out.println(" - " + id + " added for both players");
        }
        catch (GameException e) {
            throw e;
        }
    }

    public void viewBattleField() throws GameException {
        if (!gameInitialized) {
            throw new GameException("Game not initialized");
        }

        int size = battlefield.getSize();
        String[][] grid = new String[size][size];

        // Initialize grid with empty spaces
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = "| " + i + "," + j + " ";
            }
        }

        // Place ships on grid
        for (Ship ship : battlefield.getShipsByPlayer(Player.PLAYER_A)) {
            for (Coordinate pos : ship.getOccupiedCoordinates()) {
                grid[pos.getX()][pos.getY()] = String.format("|A-%-2s", ship.getId());
            }
        }

        for (Ship ship : battlefield.getShipsByPlayer(Player.PLAYER_B)) {
            for (Coordinate pos : ship.getOccupiedCoordinates()) {
                grid[pos.getX()][pos.getY()] = String.format("|B-%-2s", ship.getId());
            }
        }

        // Print grid
        System.out.println("Board / Battlefield :");
        for (int y = size - 1; y >= 0; y--) {
            for (int x = 0; x < size; x++) {
                System.out.print(grid[x][y]);
            }
            System.out.println();
        }
    }

}
