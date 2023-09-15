package ru.jefremov.prog.exceptions.script;

public class ScriptRecursionDepthException extends ScriptLaunchException{
    public ScriptRecursionDepthException(String message) {
        super("Too many scripts are launching new scripts.\n"+message);
    }
}
