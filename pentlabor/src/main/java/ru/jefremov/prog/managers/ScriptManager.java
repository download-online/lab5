package ru.jefremov.prog.managers;

import ru.jefremov.prog.exceptions.script.*;
import ru.jefremov.prog.interaction.Printer;
import ru.jefremov.prog.interaction.Submitter;
import ru.jefremov.prog.script.Script;

import java.util.ArrayDeque;

public class ScriptManager implements Submitter<String> {
    private final ArrayDeque<Script> scriptQueue = new ArrayDeque<>();
    private final int capacity;

    public ScriptManager(int capacity) {
        this.capacity = capacity;
    }

    public void startScript(Script script) throws ScriptRecursionException, ScriptRecursionDepthException {
        if (script==null) {
            throw new IllegalArgumentException("Script must not be null");
        }
        if (scriptQueue.size()==capacity) {
            String tracing = traceLine();
            stopScriptRunning();
            throw new ScriptRecursionDepthException(tracing);
        } else if (scriptQueue.contains(script)) {
            String tracing = traceLine();
            stopScriptRunning();
            throw new ScriptRecursionException(tracing);
        }
        scriptQueue.add(script);
    }
    public void stopCurrentScript() {
        scriptQueue.removeLast();
    }

    public void stopScriptRunning() {
        scriptQueue.clear();
    }

    public boolean hasNext() {
        if (!scriptQueue.isEmpty()) {
            if (!scriptQueue.getLast().hasNext()) {
                stopCurrentScript();
                return hasNext();
            }
            return true;
        }
        return false;
    }

    public String next() {
        String line = scriptQueue.getLast().next();
        if (!line.contains("\n") && !line.isEmpty()) Printer.println(line);
        return line;
    }

    public String traceLine() {
        StringBuilder tracing = new StringBuilder();
        for (Script script : scriptQueue) {
            tracing.append("at ").append(script.getTrace()).append("\n");
        }
        return tracing.toString();
    }
}
