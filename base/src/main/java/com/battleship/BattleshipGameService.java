package com.battleship;

import com.battleship.enums.Player;

import com.battleship.models.Battlefield;
import com.battleship.models.Coordinate;
import com.battleship.models.Ship;
import com.battleship.strategy.IFiringStrategy;
import com.battleship.strategy.RandomFiringStrategy;
import com.battleship.utils.WriterUtils;

public class BattleshipGameService {
    private final Battlefield battlefield;
    private final IFiringStrategy firingStrategy;
    private Player currentPlayer;
    private boolean gameInitialized;
    private boolean gameStarted;

    public BattleshipGameService() {
        this.battlefield = new Battlefield();
        this.firingStrategy = new RandomFiringStrategy();
        this.currentPlayer = Player.PLAYER_A; // Default starting player
        this.gameInitialized = false;
        this.gameStarted = false;
    }

    public void initGame(int n) throws Exception {
        if (n <= 0 || n % 2 != 0) {
            throw new Exception("Board size must be a positive even number");
        }
        battlefield.initialize(n);
        gameInitialized = true;
        System.out.println(String.format("Game initialized on a board of size : %d x %d", n, n));
    }

    public void addShip(String id, int size, int xA, int yA, int xB, int yB) throws Exception {
        if (!gameInitialized) {
            throw new Exception("Game not initialized");
        }
        
        if (gameStarted) {
            throw new Exception("Cannot add ships after game has started");
        }
        
        if (size <= 0) {
            throw new Exception("Ship size must be positive");
        }
        
        if (id == null || id.trim().isEmpty()) {
            throw new Exception("Ship ID cannot be null or empty");
        }

        try{
            Ship shipA = new Ship(id, size, new Coordinate(xA, yA), Player.PLAYER_A);
            battlefield.addShip(shipA);
        
            Ship shipB = new Ship(id, size, new Coordinate(xB, yB), Player.PLAYER_B);
            battlefield.addShip(shipB);

            System.out.println(" - " + id + " added for both players");
        }
        catch (Exception e) {
            throw e;
        }
    }

    public void viewBattleField() throws Exception {
        if (!gameInitialized) {
            throw new Exception("Game not initialized");
        }
        WriterUtils.printBattleField(battlefield);
    }

    public void startGame() throws Exception {
        if (!gameInitialized) {
            throw new Exception("Game not initialized");
        }
        
        if (battlefield.getShipsByPlayer(Player.PLAYER_A).isEmpty()) {
            throw new Exception("No ships added to the game");
        }
        
        gameStarted = true;
        currentPlayer = Player.PLAYER_A;
        
        System.out.println("Game started! " + currentPlayer + " goes first.");
        
        while (!isGameOver()) {
            playTurn();
        }
        
        announceWinner();
    }
    
    private void playTurn() {
        try {
            Player opponent = currentPlayer.getOpponent();

            Coordinate target = firingStrategy.generateTarget(battlefield.getSize(), opponent, battlefield.getFiredPositions());
            battlefield.getFiredPositions().add(target);

            Ship targetShip = battlefield.findShipAtPosition(target);
            if(targetShip != null){
                targetShip.setDestroyed(true);
                WriterUtils.printFireResult(currentPlayer, target, opponent, targetShip, battlefield.getActiveShipCount(Player.PLAYER_A), battlefield.getActiveShipCount(Player.PLAYER_B));
            }
            else {
                WriterUtils.printFireResult(currentPlayer, target, opponent, battlefield.getActiveShipCount(Player.PLAYER_A), battlefield.getActiveShipCount(Player.PLAYER_B));
            }
            // WriterUtils.printBattleField(battlefield);
            currentPlayer = opponent;
        } catch (Exception e) {
            System.err.println("Error during turn: " + e.getMessage());
        }
    }
    
    private boolean isGameOver() {
        return battlefield.getActiveShipCount(Player.PLAYER_A) == 0 || 
               battlefield.getActiveShipCount(Player.PLAYER_B) == 0;
    }
    
    private void announceWinner() {
        if (battlefield.getActiveShipCount(Player.PLAYER_A) == 0) {
            System.out.println("GameOver. " + Player.PLAYER_B + " wins.");
        } else {
            System.out.println("GameOver. " + Player.PLAYER_A + " wins.");
        }
    }

}
