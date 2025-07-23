package com.battleship.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.battleship.enums.Player;
import com.battleship.utils.ValidatorUtils;

import lombok.Getter;

@Getter
public class Battlefield {
    private int size;
    private final Map<String, Ship> ships = new HashMap<>();
    private final Set<Coordinate> firedPositions = new HashSet<>();

    public void initialize(int size){
        this.size = size;
        ships.clear();
        firedPositions.clear();
    }

    public void addShip(Ship ship) throws Exception {
            ValidatorUtils.validateShipPlacement(ship, size, ships);
            ships.put(ship.getId() + ship.getOwner(), ship);
            System.out.println(" -- " + ship.getId() + " added for " + ship.getOwner());
    }

    public List<Ship> getShipsByPlayer(Player player) {
        return ships.values().stream()
                .filter(ship -> ship.getOwner() == player)
                .collect(Collectors.toList());
    }  
    
    public long getActiveShipCount(Player player) {
        return ships.values().stream()
                .filter(ship -> ship.getOwner() == player && !ship.isDestroyed())
                .count();
    }

    public Ship findShipAtPosition(Coordinate coordinate) {
        return ships.values().stream()
                .filter(ship -> !ship.isDestroyed() && ship.occupiesCoordinate(coordinate))
                .findFirst()
                .orElse(null);
    }
}
