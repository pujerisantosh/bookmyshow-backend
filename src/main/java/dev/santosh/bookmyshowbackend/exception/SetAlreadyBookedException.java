package dev.santosh.bookmyshowbackend.exception;

public class SetAlreadyBookedException extends RuntimeException{

    public SetAlreadyBookedException(String message) {
        super(message);
    }
}
