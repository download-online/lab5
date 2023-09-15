package ru.jefremov.prog.commands.concrete;

import ru.jefremov.prog.commands.AbstractCommand;
import ru.jefremov.prog.exceptions.SavedCollectionInteractionException;
import ru.jefremov.prog.interaction.Printer;
import ru.jefremov.prog.managers.CommandManager;

/**
 * Команда для сохранения коллекции в файл.
 */
public class SaveCommand extends AbstractCommand {
    public SaveCommand(String word, CommandManager manager) {
        super(word, manager);
    }

    public SaveCommand(String word, CommandManager manager, String description) {
        super(word, manager, description);
    }

    @Override
    public void execute() {
        try {
            administrator.collectionFileInteraction.saveCollection(storage.getCollectionCopy());
            Printer.println("The collection was successfully saved.");
        } catch (SavedCollectionInteractionException e) {
            Printer.println(e.getMessage());
            Printer.println("Collection saving failed");
        }
    }
}
