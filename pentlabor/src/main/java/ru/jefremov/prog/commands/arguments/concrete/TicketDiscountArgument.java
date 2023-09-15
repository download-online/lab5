package ru.jefremov.prog.commands.arguments.concrete;

import ru.jefremov.prog.commands.Argumentable;
import ru.jefremov.prog.commands.arguments.ArgumentPlacement;
import ru.jefremov.prog.commands.arguments.primitive.DoubleArgument;
import ru.jefremov.prog.exceptions.command.IllegalCommandArgumentException;

/**
 * Аргумент, отвечающий за скидку билета.
 */
public class TicketDiscountArgument extends DoubleArgument {
    public TicketDiscountArgument(String name, ArgumentPlacement placement, Argumentable argumentable, String regex) {
        super(name, placement, argumentable, regex);
    }

    @Override
    protected boolean checkValue(Double value) throws IllegalCommandArgumentException {
        String comment = administrator.ticketValidator.reviewDiscount(value);
        if (comment!=null) throw new IllegalCommandArgumentException(name, comment);
        return true;
    }
}
