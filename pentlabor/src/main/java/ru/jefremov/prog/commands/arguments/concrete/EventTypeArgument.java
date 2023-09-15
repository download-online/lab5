package ru.jefremov.prog.commands.arguments.concrete;

import ru.jefremov.prog.commands.Argumentable;
import ru.jefremov.prog.commands.arguments.ArgumentPlacement;
import ru.jefremov.prog.commands.arguments.ArgumentType;
import ru.jefremov.prog.commands.arguments.PrimitiveArgument;
import ru.jefremov.prog.exceptions.command.IllegalCommandArgumentException;
import ru.jefremov.prog.exceptions.command.InvalidCommandArgumentException;
import ru.jefremov.prog.models.EventType;

public class EventTypeArgument extends PrimitiveArgument<EventType> {
    public EventTypeArgument(String name, ArgumentPlacement placement, Argumentable argumentable, String regex) {
        super(name, placement, argumentable, regex, ArgumentType.ENUM);
    }

    @Override
    protected EventType parseValue(String textForm) throws InvalidCommandArgumentException, IllegalCommandArgumentException {
        if (textForm==null || textForm.isBlank()) return null;
        return EventType.valueOf(textForm.toUpperCase());
    }

    @Override
    protected boolean checkValue(EventType value) throws IllegalCommandArgumentException {
        return administrator.eventValidator.checkEventType(value);
    }

    @Override
    public String getInvitation() {
        StringBuilder invitation = new StringBuilder("Choose " + name + " from the list: \n");
        for (EventType value :
                EventType.values()) {
            invitation.append(value.ordinal() + 1).append(". ").append(value.toString().toLowerCase()).append('\n');
        }
        return invitation.toString();
    }
}
