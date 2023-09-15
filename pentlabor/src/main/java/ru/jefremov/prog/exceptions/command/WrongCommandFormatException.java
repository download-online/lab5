package ru.jefremov.prog.exceptions.command;

public class WrongCommandFormatException extends CommandLaunchException {
    public WrongCommandFormatException() {
    }

    public WrongCommandFormatException(String message) {
        super(message);
    }

    public WrongCommandFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongCommandFormatException(Throwable cause) {
        super(cause);
    }

    public WrongCommandFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
