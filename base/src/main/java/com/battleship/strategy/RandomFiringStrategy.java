package com.battleship.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.battleship.enums.Player;
import com.battleship.models.Coordinate;

public class RandomFiringStrategy implements IFiringStrategy{
    private final Random random = new Random();
    
    @Override
    public Coordinate generateTarget(int battlefieldSize, Player targetPlayer, Set<Coordinate> firedPositions) {
        List<Coordinate> availablePositions = new ArrayList<>();
        
        int startX = (targetPlayer == Player.PLAYER_A) ? 0 : battlefieldSize / 2;
        int endX = (targetPlayer == Player.PLAYER_A) ? battlefieldSize / 2 : battlefieldSize;
        
        for (int x = startX; x < endX; x++) {
            for (int y = 0; y < battlefieldSize; y++) {
                Coordinate pos = new Coordinate(x, y);
                if (!firedPositions.contains(pos)) {
                    availablePositions.add(pos);
                }
            }
        }
        
        if (availablePositions.isEmpty()) {
            throw new RuntimeException("No more positions available to fire at");
        }
        
        return availablePositions.get(random.nextInt(availablePositions.size()));
    }
}
