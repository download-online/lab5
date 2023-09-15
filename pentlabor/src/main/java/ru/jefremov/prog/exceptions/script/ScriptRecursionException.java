package ru.jefremov.prog.exceptions.script;

public class ScriptRecursionException extends ScriptLaunchException{
    public ScriptRecursionException(String message) {
        super("The script runs itself.\n"+message);
    }
}
