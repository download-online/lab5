package ru.jefremov.prog.modes;

import ru.jefremov.prog.exceptions.script.ScriptRecursionDepthException;
import ru.jefremov.prog.exceptions.script.ScriptRecursionException;
import ru.jefremov.prog.interaction.Printer;
import ru.jefremov.prog.managers.ScriptManager;
import ru.jefremov.prog.script.Script;

/**
 * Класс для режима работы, при котором команды поставляются из скрипта.
 */
public class ScriptMode extends Mode{
    /**
     * Конструктор для скриптового режима
     * @param next следующий режим
     * @param submitter поставщик комманд из скриптов
     */
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

    /**
     * Запуск скрипта
     * @param script объект скрипта
     * @throws ScriptRecursionException вызывается, если скрипт вызывает сам себя (косвенно или напрямую).
     * @throws ScriptRecursionDepthException вызывается, если скрипт рекурсивно запускает слишком много скриптов.
     */
    public void startScript(Script script) throws ScriptRecursionException, ScriptRecursionDepthException {
        getSubmitter().startScript(script);
    }
}
