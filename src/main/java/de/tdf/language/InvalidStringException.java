package de.tdf.language;

public class InvalidStringException extends RuntimeException {

    public InvalidStringException() {
    }

    public InvalidStringException(String message) {
        super(message);
    }

    public InvalidStringException(Throwable throwable) {
        super(throwable);
    }
}
