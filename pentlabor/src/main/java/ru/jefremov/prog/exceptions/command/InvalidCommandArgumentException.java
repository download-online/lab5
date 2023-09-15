package ru.jefremov.prog.exceptions.command;

public class InvalidCommandArgumentException extends CommandArgumentException {
    public InvalidCommandArgumentException() {
    }

    public InvalidCommandArgumentException(String message) {
        super(message);
    }

    public InvalidCommandArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCommandArgumentException(Throwable cause) {
        super(cause);
    }

    public InvalidCommandArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
