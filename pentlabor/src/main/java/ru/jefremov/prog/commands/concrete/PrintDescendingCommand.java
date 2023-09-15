package ru.jefremov.prog.commands.concrete;

import ru.jefremov.prog.commands.AbstractCommand;
import ru.jefremov.prog.interaction.Printer;
import ru.jefremov.prog.managers.CommandManager;

/**
 * Команда, выводящая содержимое коллекции в порядке убывания.
 */
public class PrintDescendingCommand extends AbstractCommand {
    public PrintDescendingCommand(String word, CommandManager manager) {
        super(word, manager);
    }

    public PrintDescendingCommand(String word, CommandManager manager, String description) {
        super(word, manager, description);
    }

    @Override
    public void execute() {
        boolean result = storage.printDescending();
        if (!result) Printer.println("Collection is empty");
    }
}
