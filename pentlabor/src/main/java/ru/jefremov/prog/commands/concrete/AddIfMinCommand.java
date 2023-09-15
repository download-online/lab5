package ru.jefremov.prog.commands.concrete;

import ru.jefremov.prog.commands.AbstractCommand;
import ru.jefremov.prog.commands.arguments.complex.TicketArgument;
import ru.jefremov.prog.interaction.Printer;
import ru.jefremov.prog.managers.CommandManager;
import ru.jefremov.prog.models.Ticket;

/**
 * Команда, добавляющая билет в коллекцию, если он меньше всех её элементов.
 */
public class AddIfMinCommand extends AbstractCommand {
    private final TicketArgument arg1 = new TicketArgument("Ticket", this);
    public AddIfMinCommand(String word, CommandManager manager) {
        super(word, manager);
    }

    public AddIfMinCommand(String word, CommandManager manager, String description) {
        super(word, manager, description);
    }

    @Override
    public void execute() {
        Ticket ticket = arg1.getValue();
        boolean success = storage.addIfMin(ticket);
        Printer.println(success? "Ticket successfully added." : "Failed to add ticket: the specified ticket is not minimal.");
    }
}
