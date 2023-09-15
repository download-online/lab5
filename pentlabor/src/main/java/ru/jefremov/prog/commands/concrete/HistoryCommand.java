package ru.jefremov.prog.commands.concrete;

import ru.jefremov.prog.commands.AbstractCommand;
import ru.jefremov.prog.managers.CommandManager;

public class HistoryCommand extends AbstractCommand {
    public HistoryCommand(String word, CommandManager manager) {
        super(word, manager);
    }

    public HistoryCommand(String word, CommandManager manager, String description) {
        super(word, manager, description);
    }

    @Override
    public void execute() {
        manager.printHistory();
    }
}
