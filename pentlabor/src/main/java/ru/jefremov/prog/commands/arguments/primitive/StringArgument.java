package ru.jefremov.prog.commands.arguments.primitive;

import ru.jefremov.prog.commands.Argumentable;
import ru.jefremov.prog.commands.arguments.ArgumentPlacement;
import ru.jefremov.prog.commands.arguments.ArgumentType;
import ru.jefremov.prog.commands.arguments.PrimitiveArgument;
import ru.jefremov.prog.exceptions.command.IllegalCommandArgumentException;
import ru.jefremov.prog.exceptions.command.InvalidCommandArgumentException;

public class StringArgument extends PrimitiveArgument<String> {
    public StringArgument(String name, ArgumentPlacement placement, Argumentable argumentable, String regex) {
        super(name, placement, argumentable, regex, ArgumentType.STRING);
    }


    @Override
    protected String parseValue(String textForm) throws InvalidCommandArgumentException, IllegalCommandArgumentException {
        return administrator.quotationHandler.handle(textForm);
    }
}
