package ru.jefremov.prog.commands.arguments.concrete;

import ru.jefremov.prog.commands.Argumentable;
import ru.jefremov.prog.commands.arguments.ArgumentPlacement;
import ru.jefremov.prog.commands.arguments.primitive.IntegerArgument;
import ru.jefremov.prog.exceptions.command.IllegalCommandArgumentException;

public class CoordinatesXArgument extends IntegerArgument {
    public CoordinatesXArgument(String name, ArgumentPlacement placement, Argumentable argumentable, String regex) {
        super(name, placement, argumentable, regex);
    }

    @Override
    protected boolean checkValue(Integer value) throws IllegalCommandArgumentException {
        String comment = administrator.coordinatesValidator.reviewX(value);
        if (comment!=null) throw new IllegalCommandArgumentException(name, comment);
        return true;
    }
}
