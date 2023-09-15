package ru.jefremov.prog.commands.arguments.primitive;

import ru.jefremov.prog.commands.Argumentable;
import ru.jefremov.prog.commands.arguments.ArgumentPlacement;
import ru.jefremov.prog.commands.arguments.ArgumentType;
import ru.jefremov.prog.commands.arguments.PrimitiveArgument;
import ru.jefremov.prog.exceptions.command.IllegalCommandArgumentException;
import ru.jefremov.prog.exceptions.command.InvalidCommandArgumentException;

public class LongArgument extends PrimitiveArgument<Long> {
    public LongArgument(String name, ArgumentPlacement placement, Argumentable argumentable, String regex) {
        super(name, placement, argumentable, regex, ArgumentType.LONG);
    }

    @Override
    protected Long parseValue(String text) throws InvalidCommandArgumentException, IllegalCommandArgumentException {
        return Long.parseLong(text);
    }
}
