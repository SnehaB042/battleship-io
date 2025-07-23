package com.battleship.exceptions;

public class InvalidPositionException extends GameException {
    public InvalidPositionException(String message) {
        super(message);
    }
}
