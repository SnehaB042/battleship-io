package com.battleship.utils;

import com.battleship.enums.Player;
import com.battleship.models.Battlefield;
import com.battleship.models.Coordinate;
import com.battleship.models.Ship;

public class WriterUtils {

    public static void printFireResult(Player currentPlayer, Coordinate target, Player opponent, Ship hitShip, long playerAShipCount, long playerBShipCount) {
        System.out.printf("%s's turn: Missile fired at %s : \"Hit\" : %s-%s destroyed : Ships Remaining - %s:%d, %s:%d%n",
                    currentPlayer, target, opponent, hitShip.getId(),
                    Player.PLAYER_A, playerAShipCount,
                    Player.PLAYER_B, playerBShipCount);
    }

    public static void printFireResult(Player currentPlayer, Coordinate target, Player opponent, long playerAShipCount, long playerBShipCount) {
        System.out.printf("%s's turn: Missile fired at %s : \"Missed\". Ships Remaining - %s:%d, %s:%d%n",
                    currentPlayer, target, opponent,
                    Player.PLAYER_A, playerAShipCount,
                    Player.PLAYER_B, playerBShipCount);
    }

    public static void printBattleField(Battlefield battlefield) {
        
        int size = battlefield.getSize();
        String[][] grid = new String[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = "| " + i + "," + j + " ";
            }
        }

        for (Ship ship : battlefield.getShipsByPlayer(Player.PLAYER_A)) {
            if(!ship.isDestroyed()){
                for (Coordinate pos : ship.getOccupiedCoordinates()) {
                grid[pos.getX()][pos.getY()] = String.format("|A-%-2s", ship.getId());
            }
            }            
        }

        for (Ship ship : battlefield.getShipsByPlayer(Player.PLAYER_B)) {
            if(!ship.isDestroyed()){
            for (Coordinate pos : ship.getOccupiedCoordinates()) {
                grid[pos.getX()][pos.getY()] = String.format("|B-%-2s", ship.getId());
            }
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
