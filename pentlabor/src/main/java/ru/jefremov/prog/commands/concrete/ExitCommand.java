package ru.jefremov.prog.commands.concrete;

import ru.jefremov.prog.commands.AbstractCommand;
import ru.jefremov.prog.exceptions.ExitInterruptionException;
import ru.jefremov.prog.managers.CommandManager;

/**
 * Команда, обеспечивающая завершение программы без сохранения.
 */
public class ExitCommand extends AbstractCommand {
    public ExitCommand(String word, CommandManager manager) {
        super(word, manager);
    }

    public ExitCommand(String word, CommandManager manager, String description) {
        super(word, manager, description);
    }

    @Override
    public void execute() throws ExitInterruptionException {
        throw new ExitInterruptionException();
    }
}
