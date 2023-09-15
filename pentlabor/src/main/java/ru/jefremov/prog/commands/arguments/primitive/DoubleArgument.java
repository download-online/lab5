package ru.jefremov.prog.commands.arguments.primitive;

import ru.jefremov.prog.commands.Argumentable;
import ru.jefremov.prog.commands.arguments.ArgumentPlacement;
import ru.jefremov.prog.commands.arguments.ArgumentType;
import ru.jefremov.prog.commands.arguments.PrimitiveArgument;
import ru.jefremov.prog.exceptions.command.IllegalCommandArgumentException;
import ru.jefremov.prog.exceptions.command.InvalidCommandArgumentException;

/**
 * Дробный аргумент
 */
public class DoubleArgument extends PrimitiveArgument<Double> {
    public DoubleArgument(String name, ArgumentPlacement placement, Argumentable argumentable, String regex) {
        super(name, placement, argumentable, regex, ArgumentType.DOUBLE);
    }



    @Override
    protected Double parseValue(String textForm) throws InvalidCommandArgumentException, IllegalCommandArgumentException {
        int digitCount = textForm.length();
        if (textForm.contains(".")) digitCount -= 1;
        if (textForm.contains("-")) digitCount -= 1;
        if (digitCount>15) throw new InvalidCommandArgumentException("The entered value contains too many digits.");
        return Double.valueOf(textForm);
    }
}
