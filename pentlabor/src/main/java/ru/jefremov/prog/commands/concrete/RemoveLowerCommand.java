package ru.jefremov.prog.commands.concrete;

import ru.jefremov.prog.commands.AbstractCommand;
import ru.jefremov.prog.commands.arguments.complex.TicketArgument;
import ru.jefremov.prog.interaction.Printer;
import ru.jefremov.prog.managers.CommandManager;
import ru.jefremov.prog.models.Ticket;

/**
 * Команда, удаляющая все элементы, меньшие, чем заданный.
 */
public class RemoveLowerCommand extends AbstractCommand {
    private final TicketArgument arg1 = new TicketArgument("Ticket", this);
    public RemoveLowerCommand(String word, CommandManager manager) {
        super(word, manager);
    }

    public RemoveLowerCommand(String word, CommandManager manager, String description) {
        super(word, manager, description);
    }

    @Override
    public void execute() {
        Ticket ticket = arg1.getValue();
        int result = storage.removeLower(ticket);
        Printer.println(result>0? "Removed "+result+" tickets." : "Not a single ticket has been removed");
    }
}
