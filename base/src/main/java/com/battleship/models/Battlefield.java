package com.battleship.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.battleship.enums.Player;
import com.battleship.exceptions.GameException;

import lombok.Getter;

@Getter
public class Battlefield {
    private int size;
    private final Map<String, Ship> ships = new HashMap<>();

    public void initialize(int size){
        this.size = size;
        ships.clear();
    }

    public void addShip(Ship ship) throws GameException {
            validateShipPlacement(ship);
            ships.put(ship.getId() + ship.getOwner(), ship);
            System.out.println(" -- Ship " + ship.getId() + " added for " + ship.getOwner());
    }

    // todo : write test cases for validating ship placement
    private void validateShipPlacement(Ship ship) throws GameException {
        Coordinate center = ship.getCenter();
        int shipSize = ship.getSize();
        Player owner = ship.getOwner();

        int x = center.getX();
        int y = center.getY();

        if (x - (shipSize / 2) < 0 || y - (shipSize / 2) < 0 ||
            x + (shipSize / 2) > size || y + (shipSize / 2) > size) {
            throw new GameException("Ship goes out of battlefield bounds");
        }
        
        if (owner == Player.PLAYER_A) {
            if (x + (shipSize / 2) > size / 2) {
                throw new GameException("Ship extends beyond PlayerA's territory");
            }
        } else {

            if (x - (shipSize / 2) < size / 2) {
                throw new GameException("Ship extends beyond PlayerB's territory");
            }
        }
        
        List<Coordinate> newShipPositions = ship.getOccupiedCoordinates();
        for (Ship existingShip : ships.values()) {
            List<Coordinate> existingPositions = existingShip.getOccupiedCoordinates();
            for (Coordinate newPos : newShipPositions) {
                if (existingPositions.contains(newPos)) {
                    throw new GameException("Ship overlaps with existing ship: " + existingShip.getId());
                }
            }
        }
    }

    public List<Ship> getShipsByPlayer(Player player) {
        return ships.values().stream()
                .filter(ship -> ship.getOwner() == player)
                .collect(Collectors.toList());
    }



    
}
