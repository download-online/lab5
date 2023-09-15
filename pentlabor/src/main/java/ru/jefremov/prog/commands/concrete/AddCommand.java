package ru.jefremov.prog.commands.concrete;

import ru.jefremov.prog.commands.AbstractCommand;
import ru.jefremov.prog.commands.arguments.complex.TicketArgument;
import ru.jefremov.prog.interaction.Printer;
import ru.jefremov.prog.managers.CommandManager;
import ru.jefremov.prog.models.Ticket;

/**
 * Команда, добавляющая билет в коллекцию.
 */
public class AddCommand extends AbstractCommand {
    private final TicketArgument arg1 = new TicketArgument("Ticket", this);
    public AddCommand(String word, CommandManager manager) {
        super(word, manager);
    }

    public AddCommand(String word, CommandManager manager, String description) {
        super(word, manager, description);
    }

    @Override
    public void execute() {
        Ticket ticket = arg1.getValue();
        boolean success = storage.addTicket(ticket);
        if (success) {
            Printer.println("Ticket successfully added.\n"+ticket);
        } else {
            Printer.println("Failed to add ticket.");
        }
    }
}
