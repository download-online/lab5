package ru.jefremov.prog.exceptions.script;

public class ScriptLaunchException extends Exception {
    public ScriptLaunchException(String message) {
        super("Error during script execution:\n" + message);
    }
}