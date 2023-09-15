package ru.jefremov.prog.commands;

import ru.jefremov.prog.commands.arguments.AbstractArgument;

public interface Argumentable {
    AbstractCommand referToCommand();

    default void acceptArgument(AbstractArgument<?> argument) {
        if (argument==null) {
            throw new IllegalArgumentException("Configuration problem: argument should exist");
        }
        if (argument.getCommand()!=referToCommand()) {
            throw new IllegalArgumentException("Argument doesn't refer to this command");
        }
        if (argument.isAttached()) {
            throw new IllegalArgumentException("Argument is already attached to this command");
        }
        processAccepting(argument);
    }

    void processAccepting(AbstractArgument<?> argument);
}
