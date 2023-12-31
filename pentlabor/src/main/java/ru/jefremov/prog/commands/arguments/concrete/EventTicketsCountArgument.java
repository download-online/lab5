package ru.jefremov.prog.commands.arguments.concrete;

import ru.jefremov.prog.commands.Argumentable;
import ru.jefremov.prog.commands.arguments.ArgumentPlacement;
import ru.jefremov.prog.commands.arguments.primitive.LongArgument;
import ru.jefremov.prog.exceptions.command.IllegalCommandArgumentException;

public class EventTicketsCountArgument extends LongArgument {
    public EventTicketsCountArgument(String name, ArgumentPlacement placement, Argumentable argumentable, String regex) {
        super(name, placement, argumentable, regex);
    }

    @Override
    protected boolean checkValue(Long value) throws IllegalCommandArgumentException {
        String comment = administrator.eventValidator.reviewTicketsCount(value);
        if (comment!=null) throw new IllegalCommandArgumentException(name, comment);
        return true;
    }
}
