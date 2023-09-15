package ru.jefremov.prog.commands.concrete;

import ru.jefremov.prog.commands.AbstractCommand;
import ru.jefremov.prog.commands.arguments.ArgumentPlacement;
import ru.jefremov.prog.commands.arguments.concrete.TicketCommentArgument;
import ru.jefremov.prog.commands.arguments.primitive.StringArgument;
import ru.jefremov.prog.interaction.Printer;
import ru.jefremov.prog.managers.CommandManager;

/**
 * Команда, выводящая билеты, чей комментарий начинается с заданной подстроки.
 */
public class FilterStartsWithCommentCommand extends AbstractCommand {
    private final StringArgument arg1 = new TicketCommentArgument("comment", ArgumentPlacement.INLINE,this,null);
    public FilterStartsWithCommentCommand(String word, CommandManager manager) {
        super(word, manager);
    }

    public FilterStartsWithCommentCommand(String word, CommandManager manager, String description) {
        super(word, manager, description);
    }

    @Override
    public void execute() {
        String comment = arg1.getValue();
        boolean success = storage.printFilterStartsWithComment(comment);
        if (!success) Printer.println("No ticket has a comment starting from the specified line.");
    }
}
