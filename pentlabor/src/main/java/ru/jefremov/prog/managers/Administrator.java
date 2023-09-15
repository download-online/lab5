package ru.jefremov.prog.managers;

import ru.jefremov.prog.handlers.EmptinessHandler;
import ru.jefremov.prog.handlers.Handler;
import ru.jefremov.prog.handlers.QuotationHandler;
import ru.jefremov.prog.interaction.CollectionFileInteraction;
import ru.jefremov.prog.interaction.InteractiveSubmitter;
import ru.jefremov.prog.models.validation.CoordinatesValidator;
import ru.jefremov.prog.models.validation.EventValidator;
import ru.jefremov.prog.models.validation.TicketValidator;
import ru.jefremov.prog.modes.InteractiveMode;
import ru.jefremov.prog.modes.ScriptMode;

/**
 * Класс, основной задачей которого служит сборка остальных компонентов.
 */
public class Administrator {
    public final String collectionFile;
    public final EventValidator eventValidator = new EventValidator();
    public final CoordinatesValidator coordinatesValidator = new CoordinatesValidator();
    public final TicketValidator ticketValidator = new TicketValidator(eventValidator,coordinatesValidator);
    public final Storage storage = new Storage(this, ticketValidator);
    public final ScriptManager scriptManager = new ScriptManager(5);
    public final InteractiveSubmitter interactiveSubmitter;
    public final InteractiveMode interactiveMode;
    public final ScriptMode scriptMode;
    public final ModeManager modeManager;
    public final Handler<String> quotationHandler = new EmptinessHandler(new QuotationHandler());
    public final CommandManager commandManager;
    public final CollectionFileInteraction collectionFileInteraction;

    /**
     * Конструктор для администратора
     * @param interactiveSubmitter интерактивный поставщик строк
     * @param path путь к файлу, содержащему коллекцию
     */
    public Administrator(InteractiveSubmitter interactiveSubmitter, String path) {
        this.interactiveSubmitter = interactiveSubmitter;
        interactiveMode = new InteractiveMode(null, interactiveSubmitter);
        scriptMode  = new ScriptMode(interactiveMode, scriptManager);
        modeManager = new ModeManager(interactiveMode,scriptMode);
        commandManager = new CommandManager(storage);
        collectionFileInteraction = new CollectionFileInteraction(path);
        collectionFile = path;
    }
}
