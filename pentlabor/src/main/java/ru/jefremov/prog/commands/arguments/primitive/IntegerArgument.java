package ru.jefremov.prog.commands.arguments.primitive;

import ru.jefremov.prog.commands.Argumentable;
import ru.jefremov.prog.commands.arguments.ArgumentPlacement;
import ru.jefremov.prog.commands.arguments.ArgumentType;
import ru.jefremov.prog.commands.arguments.PrimitiveArgument;
import ru.jefremov.prog.exceptions.command.IllegalCommandArgumentException;
import ru.jefremov.prog.exceptions.command.InvalidCommandArgumentException;

/**
 * Целочисленный аргумент типа int
 */
public class IntegerArgument extends PrimitiveArgument<Integer> {
    public IntegerArgument(String name, ArgumentPlacement placement, Argumentable argumentable, String regex) {
        super(name, placement, argumentable, regex, ArgumentType.INTEGER);
    }

    @Override
    protected Integer parseValue(String text) throws InvalidCommandArgumentException, IllegalCommandArgumentException {
        return Integer.parseInt(text);
    }
}
