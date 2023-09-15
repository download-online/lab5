package ru.jefremov.prog.commands.concrete;

import ru.jefremov.prog.commands.AbstractCommand;
import ru.jefremov.prog.commands.arguments.ArgumentPlacement;
import ru.jefremov.prog.commands.arguments.complex.TicketArgument;
import ru.jefremov.prog.commands.arguments.concrete.TicketIdArgument;
import ru.jefremov.prog.interaction.Printer;
import ru.jefremov.prog.managers.CommandManager;
import ru.jefremov.prog.models.Ticket;

public class UpdateCommand extends AbstractCommand {
    private final TicketIdArgument arg1 = new TicketIdArgument("id", ArgumentPlacement.INLINE,this,null);
    private final TicketArgument arg2 = new TicketArgument("Ticket", this);
    public UpdateCommand(String word, CommandManager manager) {
        super(word, manager);
    }

    public UpdateCommand(String word, CommandManager manager, String description) {
        super(word, manager, description);
    }

    @Override
    public void execute() {
        int id = arg1.getValue();
        Ticket ticket = arg2.getValue();
        boolean success = storage.updateById(id,ticket);
        Printer.println(success? "Ticket updated." : "Failed to update ticket");
    }
}
