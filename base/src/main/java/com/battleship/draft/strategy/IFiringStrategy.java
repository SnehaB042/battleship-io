package com.battleship.draft.strategy;

import java.util.Set;

import com.battleship.enums.Player;
import com.battleship.models.Coordinate;

public interface IFiringStrategy {
   Coordinate generateTarget(int battlefieldSize, Player targetPlayer, Set<Coordinate> firedPositions);
}
