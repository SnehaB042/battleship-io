package com.battleship.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Coordinate {
    Integer x;
    Integer y;

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

}
