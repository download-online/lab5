package ru.jefremov.prog.commands.concrete;

import ru.jefremov.prog.commands.AbstractCommand;
import ru.jefremov.prog.commands.arguments.ArgumentPlacement;
import ru.jefremov.prog.commands.arguments.concrete.TicketIdArgument;
import ru.jefremov.prog.interaction.Printer;
import ru.jefremov.prog.managers.CommandManager;

/**
 * Команда, удаляющая элемент по Id.
 */
public class RemoveByIdCommand extends AbstractCommand {
    private final TicketIdArgument arg1 = new TicketIdArgument("id", ArgumentPlacement.INLINE, this,null);
    public RemoveByIdCommand(String word, CommandManager manager) {
        super(word, manager);
    }

    public RemoveByIdCommand(String word, CommandManager manager, String description) {
        super(word, manager, description);
    }

    @Override
    public void execute() {
        int id = arg1.getValue();
        boolean success = storage.removeById(id);
        Printer.println(success? "Ticket removed." : "Failed to remove ticket");
    }
}
