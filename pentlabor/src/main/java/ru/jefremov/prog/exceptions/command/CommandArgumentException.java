package ru.jefremov.prog.exceptions.command;

/**
 * Общий класс для исключений, связанных с аргументами запускаемых команд.
 */
public class CommandArgumentException extends CommandLaunchException {
    public CommandArgumentException() {
    }

    public CommandArgumentException(String message) {
        super(message);
    }

    public CommandArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandArgumentException(Throwable cause) {
        super(cause);
    }

    public CommandArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
