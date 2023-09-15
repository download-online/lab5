package ru.jefremov.prog.commands.concrete;

import ru.jefremov.prog.commands.AbstractCommand;
import ru.jefremov.prog.commands.arguments.complex.EventArgument;
import ru.jefremov.prog.interaction.Printer;
import ru.jefremov.prog.managers.CommandManager;
import ru.jefremov.prog.models.Event;

public class FilterLessThanEventCommand extends AbstractCommand {
    private final EventArgument arg1 = new EventArgument("event", this);
    public FilterLessThanEventCommand(String word, CommandManager manager) {
        super(word, manager);
    }

    public FilterLessThanEventCommand(String word, CommandManager manager, String description) {
        super(word, manager, description);
    }

    @Override
    public void execute() {
        Event event = arg1.getValue();
        boolean success = storage.printFilterLessThanEvent(event);
        if (!success) Printer.println("No ticket has an event less than the specified one.");
    }
}
