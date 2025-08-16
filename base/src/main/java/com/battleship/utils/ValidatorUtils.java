package com.battleship.utils;

import java.util.List;
import java.util.Map;

import com.battleship.enums.Player;
import com.battleship.models.Coordinate;
import com.battleship.models.Ship;

public class ValidatorUtils {
    public static void validateShipPlacement(Ship newShip, int battlefieldSize, Map<String, Ship> ships) throws Exception {
        Coordinate center = newShip.getCenter();
        int shipSize = newShip.getSize();
        Player owner = newShip.getOwner();

        int x = center.getX();
        int y = center.getY();

        if (x - (shipSize / 2) < 0 || y - (shipSize / 2) < 0 ||
            x + (shipSize / 2) > battlefieldSize || y + (shipSize / 2) > battlefieldSize) {
            throw new Exception("Ship goes out of battlefield bounds");
        }
        
        if (owner == Player.PLAYER_A) {
            if (x + (shipSize / 2) > battlefieldSize / 2) {
                throw new Exception("Ship extends beyond PlayerA's territory");
            }
        } else {
            if (x - (shipSize / 2) < battlefieldSize / 2) {
                throw new Exception("Ship extends beyond PlayerB's territory");
            }
        }
        
        List<Coordinate> newShipCoordinates = newShip.getOccupiedCoordinates();
        for (Ship existingShip : ships.values()) {
            List<Coordinate> existingCoordinates = existingShip.getOccupiedCoordinates();
            for (Coordinate newPos : newShipCoordinates) {
                if (existingCoordinates.contains(newPos)) {
                    throw new Exception("Ship overlaps with existing ship: " + existingShip.getId());
                }
            }
        }
    }

    
}
