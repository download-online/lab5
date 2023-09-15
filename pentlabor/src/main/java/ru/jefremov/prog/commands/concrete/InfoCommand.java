package ru.jefremov.prog.commands.concrete;

import ru.jefremov.prog.commands.AbstractCommand;
import ru.jefremov.prog.managers.CommandManager;

/**
 * Команда, выводящая информацию о коллекции.
 */
public class InfoCommand extends AbstractCommand {
    public InfoCommand(String word, CommandManager manager) {
        super(word, manager);
    }

    public InfoCommand(String word, CommandManager manager, String description) {
        super(word, manager, description);
    }

    @Override
    public void execute() {
        storage.printInfo();
    }
}
