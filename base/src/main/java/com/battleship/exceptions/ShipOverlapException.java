package com.battleship.exceptions;

public class ShipOverlapException extends GameException {
    public ShipOverlapException(String message) {
        super(message);
    }
}
