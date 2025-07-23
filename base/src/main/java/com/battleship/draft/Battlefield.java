package com.battleship.draft;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.battleship.enums.Player;
import com.battleship.models.Coordinate;
import com.battleship.exceptions.ShipOverlapException;
import com.battleship.exceptions.InvalidPositionException;

public class Battlefield {
    private int size;
    private final Map<String, Ship> ships = new HashMap<>();

    public void initialize(int size){
        this.size = size;
        ships.clear();
    }

    

    // ship actions
    public void addShip(Ship ship){
        try {
            validateShipPlacement(ship);
            ships.put(ship.getId(), ship);

            System.out.println("Ship added: " + ship.getId() + " at " + ship.getCenter() + " for player " + ship.getOwner());
        } catch (ShipOverlapException | InvalidPositionException e) {
            System.err.println("Error adding ship: " + e.getMessage());
        }

    }

    // todo : write test cases for validating ship placement
    private void validateShipPlacement(Ship ship) throws ShipOverlapException, InvalidPositionException {
        Coordinate center = ship.getCenter();
        int shipSize = ship.getSize();
        Player owner = ship.getOwner();

        int x = center.getX();
        int y = center.getY();

        if (x - shipSize / 2 < 0 || y - shipSize / 2 < 0 ||
            x + shipSize / 2 > size || y + shipSize / 2 > size) {
            throw new InvalidPositionException("Ship goes out of battlefield bounds");
        }
        
        if (owner == Player.PLAYER_A) {
            if (x + shipSize > size / 2) {
                throw new InvalidPositionException("Ship extends beyond PlayerA's territory");
            }
        } else {
            if (x < size / 2) {
                throw new InvalidPositionException("Ship extends beyond PlayerB's territory");
            }
        }
        
        List<Coordinate> newShipPositions = ship.getOccupiedPositions();
        for (Ship existingShip : ships.values()) {
            List<Coordinate> existingPositions = existingShip.getOccupiedPositions();
            for (Coordinate newPos : newShipPositions) {
                if (existingPositions.contains(newPos)) {
                    throw new ShipOverlapException("Ship overlaps with existing ship: " + existingShip.getId());
                }
            }
        }
    }



    
}
