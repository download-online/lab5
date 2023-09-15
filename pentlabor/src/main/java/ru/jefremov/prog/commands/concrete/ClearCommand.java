package ru.jefremov.prog.commands.concrete;

import ru.jefremov.prog.commands.AbstractCommand;
import ru.jefremov.prog.interaction.Printer;
import ru.jefremov.prog.managers.CommandManager;

public class ClearCommand extends AbstractCommand {
    public ClearCommand(String word, CommandManager manager) {
        super(word, manager);
    }

    public ClearCommand(String word, CommandManager manager, String description) {
        super(word, manager, description);
    }

    @Override
    public void execute() {
        storage.clear();
        Printer.println("Collection successfully cleared.");
    }
}
