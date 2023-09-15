package ru.jefremov.prog.commands.arguments;

import ru.jefremov.prog.commands.AbstractCommand;
import ru.jefremov.prog.commands.Argumentable;
import ru.jefremov.prog.exceptions.QuitInterruptionException;
import ru.jefremov.prog.exceptions.command.CommandLaunchException;

import java.util.ArrayList;

public abstract class ComplexArgument<T> extends AbstractArgument<T> implements Argumentable {
    private final ArrayList<AbstractArgument<?>> subArguments = new ArrayList<>();

    public ComplexArgument(String name, Argumentable argumentable) {
        super(name, ArgumentPlacement.NEWLINE, argumentable, "", ArgumentType.OBJECT);
    }

    @Override
    protected final T parseValue(String text) throws CommandLaunchException, QuitInterruptionException {
        AbstractArgument.form(subArguments);
        return formComplexValue();
    }

    public abstract T formComplexValue();

    @Override
    public AbstractCommand referToCommand() {
        return getCommand();
    }

    @Override
    public final void processAccepting(AbstractArgument<?> argument) {
        if (argument.placement==ArgumentPlacement.INLINE) {
            throw new IllegalArgumentException("Cannot attach inline argument to complex argument");
        }
        subArguments.add(argument);
    }

    @Override
    public String getInvitation() {
        return "To specify "+name+", enter the following fields:\n";
    }
}
