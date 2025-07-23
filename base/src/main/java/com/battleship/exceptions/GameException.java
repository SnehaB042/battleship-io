package com.battleship.exceptions;

// futureScope: Consider adding error codes for more specific exception types
public class GameException extends Exception {
    public GameException(String message) {
        super(message);
    }
}
