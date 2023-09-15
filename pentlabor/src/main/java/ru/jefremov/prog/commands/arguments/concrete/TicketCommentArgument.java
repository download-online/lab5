package ru.jefremov.prog.commands.arguments.concrete;

import ru.jefremov.prog.commands.Argumentable;
import ru.jefremov.prog.commands.arguments.ArgumentPlacement;
import ru.jefremov.prog.commands.arguments.primitive.StringArgument;
import ru.jefremov.prog.exceptions.command.IllegalCommandArgumentException;

public class TicketCommentArgument extends StringArgument {
    public TicketCommentArgument(String name, ArgumentPlacement placement, Argumentable argumentable, String regex) {
        super(name, placement, argumentable, regex);
    }

    @Override
    protected boolean checkValue(String value) throws IllegalCommandArgumentException {
        String comment = administrator.ticketValidator.reviewComment(value);
        if (comment!=null) throw new IllegalCommandArgumentException(name, comment);
        return true;
    }


}
