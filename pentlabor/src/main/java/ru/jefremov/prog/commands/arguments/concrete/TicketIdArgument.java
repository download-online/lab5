package ru.jefremov.prog.commands.arguments.concrete;

import ru.jefremov.prog.commands.Argumentable;
import ru.jefremov.prog.commands.arguments.ArgumentPlacement;
import ru.jefremov.prog.commands.arguments.primitive.IntegerArgument;
import ru.jefremov.prog.exceptions.command.IllegalCommandArgumentException;

/**
 * Аргумент, отвечающий за идентификатор билета.
 */
public class TicketIdArgument extends IntegerArgument {
    public TicketIdArgument(String name, ArgumentPlacement placement, Argumentable argumentable, String regex) {
        super(name, placement, argumentable, regex);
    }

    @Override
    protected boolean checkValue(Integer value) throws IllegalCommandArgumentException {
        String comment = administrator.ticketValidator.reviewId(value);
        if (comment!=null) throw new IllegalCommandArgumentException(name, comment);
        if (getCommand().storage.hasId(value)) return true;
        throw new IllegalCommandArgumentException(name,"There is no ticket with specified id");
    }
}
