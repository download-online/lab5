package ru.jefremov.prog.commands.arguments.concrete;

import ru.jefremov.prog.commands.Argumentable;
import ru.jefremov.prog.commands.arguments.ArgumentPlacement;
import ru.jefremov.prog.commands.arguments.primitive.DoubleArgument;
import ru.jefremov.prog.exceptions.command.IllegalCommandArgumentException;

public class TicketPriceArgument extends DoubleArgument {
    public TicketPriceArgument(String name, ArgumentPlacement placement, Argumentable argumentable, String regex) {
        super(name, placement, argumentable, regex);
    }

    @Override
    protected boolean checkValue(Double value) throws IllegalCommandArgumentException {
        String comment = administrator.ticketValidator.reviewPrice(value);
        if (comment!=null) throw new IllegalCommandArgumentException(name, comment);
        return true;
    }
}
