package com.battleship.models;

import java.util.ArrayList;
import java.util.List;
import com.battleship.enums.Player;

import lombok.Getter;

@Getter
public class Ship {
    private final String id;
    private final int size;
    private final Coordinate center;
    private final Player owner;
    private volatile boolean isDestroyed;

    public Ship(String id, int size, Coordinate center, Player owner) {
        this.id = id;
        this.size = size;
        this.center = center;
        this.owner = owner;
        this.isDestroyed = false;
    }

    public synchronized void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    public boolean occupiesCoordinate(Coordinate coordinate) {
        int midX = center.getX();
        int midY = center.getY();
        return coordinate.getX() >= midX && coordinate.getX() < midX + size &&
               coordinate.getY() >= midY && coordinate.getY() < midY + size;
    }
    
    public List<Coordinate> getOccupiedCoordinates() {
        List<Coordinate> occupiedCoordinates = new ArrayList<>();
        int midX = center.getX();
        int midY = center.getY();
        
        for (int x = midX - (size/2); x < midX + (size/2); x++) {
            for (int y = midY - (size/2); y < midY + (size/2); y++) {
                occupiedCoordinates.add(new Coordinate(x, y));
            }
        }
        return occupiedCoordinates;
    }    
}
