package ru.jefremov.prog.modes;

import ru.jefremov.prog.exceptions.script.ScriptRecursionDepthException;
import ru.jefremov.prog.exceptions.script.ScriptRecursionException;
import ru.jefremov.prog.interaction.Printer;
import ru.jefremov.prog.managers.ScriptManager;
import ru.jefremov.prog.script.Script;

public class ScriptMode extends Mode{

    public ScriptMode(Mode next, ScriptManager submitter) {
        super("script", next, submitter, false, true);
    }

    @Override
    public Mode finish() {
        getSubmitter().stopScriptRunning();
        Printer.println("Script execution finished.");
        return getNextMode();
    }

    @Override
    public ScriptManager getSubmitter() {
        return (ScriptManager) super.getSubmitter();
    }

    public void startScript(Script script) throws ScriptRecursionException, ScriptRecursionDepthException {
        getSubmitter().startScript(script);
    }
}
