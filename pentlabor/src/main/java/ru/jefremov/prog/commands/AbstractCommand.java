package ru.jefremov.prog.commands;

import ru.jefremov.prog.commands.arguments.AbstractArgument;
import ru.jefremov.prog.exceptions.ExitInterruptionException;
import ru.jefremov.prog.exceptions.QuitInterruptionException;
import ru.jefremov.prog.exceptions.command.CommandInterruptionException;
import ru.jefremov.prog.exceptions.command.IllegalCommandArgumentException;
import ru.jefremov.prog.exceptions.command.InvalidCommandArgumentException;
import ru.jefremov.prog.exceptions.command.WrongCommandFormatException;
import ru.jefremov.prog.managers.Administrator;
import ru.jefremov.prog.managers.CommandManager;
import ru.jefremov.prog.managers.Storage;

public abstract class AbstractCommand implements Argumentable {
    public final String word;
    public final Parser parser;
    public final Storage storage;
    public final Administrator administrator;
    public final CommandManager manager;
    public final String description;

    public AbstractCommand(String word, CommandManager manager, String description) {
        if (word == null || word.isBlank()) {
            throw new IllegalArgumentException("Command should have a command word");
        }
        this.word = word;
        this.parser = new Parser(this);
        if (manager==null) throw new IllegalArgumentException("Command manager must not be null");
        manager.addCommand(this);
        this.manager = manager;
        this.storage = manager.getStorage();
        this.administrator = storage.getAdministrator();
        this.description = (description==null? "" : description);
    }

    public AbstractCommand(String word, CommandManager manager) {
        this(word,manager,"");
    }

    public final void launch(String line) throws InvalidCommandArgumentException, IllegalCommandArgumentException, WrongCommandFormatException, CommandInterruptionException, ExitInterruptionException, QuitInterruptionException {
        parser.parse(line);
        execute();
    }

    public abstract void execute() throws ExitInterruptionException;

    public final String getWord() {
        return word;
    }

    @Override
    public final AbstractCommand referToCommand() {
        return this;
    }

    @Override
    public final void processAccepting(AbstractArgument<?> argument) {
        parser.acceptArgument(argument);
    }

    @Override
    public String toString() {
        return parser.getCommandFormat()+(description.equals("")? "" : "\n--- "+description);
    }
}
