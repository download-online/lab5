package ru.jefremov.prog.exceptions.command;

public class CommandLaunchException extends Exception{
    public CommandLaunchException() {
    }

    public CommandLaunchException(String message) {
        super(message);
    }

    public CommandLaunchException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandLaunchException(Throwable cause) {
        super(cause);
    }

    public CommandLaunchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
