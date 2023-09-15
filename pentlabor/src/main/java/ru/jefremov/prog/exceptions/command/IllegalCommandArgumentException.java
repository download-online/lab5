package ru.jefremov.prog.exceptions.command;

public class IllegalCommandArgumentException extends CommandArgumentException {
    public IllegalCommandArgumentException() {
    }

    public IllegalCommandArgumentException(String argumentName, String enteredText) {
        super("Illegal value for "+argumentName+" entered: "+enteredText);
    }

    public IllegalCommandArgumentException(String message) {
        super(message);
    }

    public IllegalCommandArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalCommandArgumentException(Throwable cause) {
        super(cause);
    }

    public IllegalCommandArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
