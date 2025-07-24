package com.battleship.models;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import com.battleship.enums.Player;
import com.battleship.utils.ValidatorUtils;

public class Battlefield {
    private volatile int size;
    private final Map<String, Ship> ships = new ConcurrentHashMap<>();
    private final Set<Coordinate> firedCoordinates = ConcurrentHashMap.newKeySet();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public Set<Coordinate> getFiredCoordinates(){
        return firedCoordinates;
    }
    
    public int getSize() {
        return size;
    }

    public void initialize(int size){
        lock.writeLock().lock();
        try {
            this.size = size;
            ships.clear();
            firedCoordinates.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void addShip(Ship ship) throws Exception {
        lock.writeLock().lock();
        try{
            ValidatorUtils.validateShipPlacement(ship, size, ships);
            ships.put(ship.getId() + ship.getOwner(), ship);
            System.out.println(" -- " + ship.getId() + " added for " + ship.getOwner());
        } finally {
            lock.writeLock().unlock();
        }
     }

    public List<Ship> getShipsByPlayer(Player player) {
        lock.readLock().lock();
        try {
            return ships.values().stream()
                .filter(ship -> ship.getOwner() == player)
                .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
}  
    
    public long getActiveShipCount(Player player) {
        lock.readLock().lock();
        try {
            return ships.values().stream()
                .filter(ship -> ship.getOwner() == player && !ship.isDestroyed())
                .count();
        } finally {
            lock.readLock().unlock();
        }
    }

    public Ship findShipAtCoordinate(Coordinate coordinate) {
        lock.readLock().lock();
        try {
            return ships.values().stream()
                .filter(ship -> !ship.isDestroyed() && ship.occupiesCoordinate(coordinate))
                .findFirst()
                .orElse(null);
        } finally {
            lock.readLock().unlock();
        }
    }
}
