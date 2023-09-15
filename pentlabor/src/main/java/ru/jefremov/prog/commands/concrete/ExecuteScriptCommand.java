package ru.jefremov.prog.commands.concrete;

import ru.jefremov.prog.commands.AbstractCommand;
import ru.jefremov.prog.commands.arguments.ArgumentPlacement;
import ru.jefremov.prog.commands.arguments.concrete.ScriptArgument;
import ru.jefremov.prog.commands.arguments.primitive.StringArgument;
import ru.jefremov.prog.exceptions.script.ScriptLaunchException;
import ru.jefremov.prog.interaction.Printer;
import ru.jefremov.prog.managers.CommandManager;
import ru.jefremov.prog.script.Script;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Команда, отвечающая за запуск скриптов из файлов.
 */
public class ExecuteScriptCommand extends AbstractCommand {
    private final StringArgument arg1 = new ScriptArgument("file_name", ArgumentPlacement.INLINE,this,null);
    public ExecuteScriptCommand(String word, CommandManager manager) {
        super(word, manager);
    }

    public ExecuteScriptCommand(String word, CommandManager manager, String description) {
        super(word, manager, description);
    }

    @Override
    public void execute() {
        String path = arg1.getValue();
        try {
            File file = new File(path);
            if (!file.exists() || !file.isFile()) {
                Printer.println("Specified script file doesn't exist.");
                return;
            }
            if (!file.canRead()) {
                Printer.println("There is no access to reading specified script file.");
                return;
            }
            Script script = new Script(file);
            administrator.modeManager.startScript(script);
        } catch (FileNotFoundException e) {
            Printer.println("File not found!");
        } catch (ScriptLaunchException e) {
            Printer.println(e.getMessage());
        }
    }
}
