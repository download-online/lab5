package ru.jefremov.prog.commands.arguments.complex;

import ru.jefremov.prog.commands.Argumentable;
import ru.jefremov.prog.commands.arguments.ArgumentPlacement;
import ru.jefremov.prog.commands.arguments.ComplexArgument;
import ru.jefremov.prog.commands.arguments.concrete.EventNameArgument;
import ru.jefremov.prog.commands.arguments.concrete.EventTicketsCountArgument;
import ru.jefremov.prog.commands.arguments.concrete.EventTypeArgument;
import ru.jefremov.prog.commands.arguments.primitive.LongArgument;
import ru.jefremov.prog.commands.arguments.primitive.StringArgument;
import ru.jefremov.prog.exceptions.command.IllegalCommandArgumentException;
import ru.jefremov.prog.models.Event;

public class EventArgument extends ComplexArgument<Event> {
    private final EventTypeArgument arg3 = new EventTypeArgument("Event type", ArgumentPlacement.NEWLINE, this, null);
    private final StringArgument arg1 = new EventNameArgument("Event name", ArgumentPlacement.NEWLINE,this,null);
    private final LongArgument arg2 = new EventTicketsCountArgument("Event tickets count", ArgumentPlacement.NEWLINE,this,null);

    public EventArgument(String name, Argumentable argumentable) {
        super(name, argumentable);
    }

    @Override
    protected boolean checkValue(Event value) throws IllegalCommandArgumentException {
        String comment = administrator.eventValidator.reviewEvent(value);
        if (comment!=null) throw new IllegalCommandArgumentException(name, comment);
        return true;
    }

    @Override
    public Event formComplexValue() {
        return new Event(arg1.getValue(), arg2.getValue(), arg3.getValue());
    }
}
