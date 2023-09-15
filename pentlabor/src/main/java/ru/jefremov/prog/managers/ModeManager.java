package ru.jefremov.prog.managers;

import ru.jefremov.prog.exceptions.QuitInterruptionException;
import ru.jefremov.prog.exceptions.script.*;
import ru.jefremov.prog.handlers.*;
import ru.jefremov.prog.interaction.Submitter;
import ru.jefremov.prog.modes.*;
import ru.jefremov.prog.script.Script;

public class ModeManager  implements Submitter<String> {
    private Mode currentMode;
    private final InteractiveMode interactiveMode;
    private final ScriptMode scriptMode;
    private final Handler<String> handlerChain = new EmptinessHandler();

    public ModeManager(InteractiveMode interactiveMode, ScriptMode scriptMode) {
        this.interactiveMode = interactiveMode;
        this.scriptMode = scriptMode;
        this.currentMode = interactiveMode;
    }

    @Override
    public boolean hasNext() throws QuitInterruptionException {
        boolean currentHas = currentMode.hasNext();
        if (!currentHas) {
            if (currentMode.isAutoSwitching()) {
                String endingMessage = getEndingMessage();
                currentMode = currentMode.getNextMode();
                throw new QuitInterruptionException(endingMessage);
            }
            return false;
        }
        return true;
    }

    @Override
    public String next() {
        String line = currentMode.next();
        if (line==null) return null;
        if (line.contains("\n") || line.isEmpty()) line = currentMode.next();
        return handlerChain.handle(line);
    }

    public void startScript(Script script) throws ScriptRecursionException, ScriptRecursionDepthException {
        this.currentMode = scriptMode;
        scriptMode.startScript(script);
    }

    public boolean canRespond() {
        return currentMode.isResponsive();
    }

    public boolean interrupt() {
        if (currentMode.isAutoSwitching()) {
            currentMode = currentMode.finish();
            return true;
        }
        return false;
    }

    public String getEndingMessage() {
        return "..."+currentMode.name+" ended.";
    }
}
