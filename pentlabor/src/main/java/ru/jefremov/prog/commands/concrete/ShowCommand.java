package ru.jefremov.prog.commands.concrete;

import ru.jefremov.prog.commands.AbstractCommand;
import ru.jefremov.prog.interaction.Printer;
import ru.jefremov.prog.managers.CommandManager;

/**
 * Команда для вывода содержимого коллекции.
 */
public class ShowCommand extends AbstractCommand {
    public ShowCommand(String word, CommandManager manager) {
        super(word, manager);
    }

    public ShowCommand(String word, CommandManager manager, String description) {
        super(word, manager, description);
    }

    @Override
    public void execute() {
        boolean result = storage.show();
        if (!result) Printer.println("Collection is empty");
    }
}
