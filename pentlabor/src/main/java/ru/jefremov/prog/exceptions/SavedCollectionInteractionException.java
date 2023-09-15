package ru.jefremov.prog.exceptions;

public class SavedCollectionInteractionException extends Exception {
    public SavedCollectionInteractionException() {
    }

    public SavedCollectionInteractionException(String message) {
        super(message);
    }

    public SavedCollectionInteractionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SavedCollectionInteractionException(Throwable cause) {
        super(cause);
    }

    public SavedCollectionInteractionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
