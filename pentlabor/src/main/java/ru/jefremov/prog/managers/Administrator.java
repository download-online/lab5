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
    /**
     * Путь к файлу с коллекцией
     */
    public final String collectionFile;
    /**
     * Валидатор событий
     */
    public final EventValidator eventValidator = new EventValidator();
    /**
     * Валидатор координат
     */
    public final CoordinatesValidator coordinatesValidator = new CoordinatesValidator();
    /**
     * Валидатор билетов
     */
    public final TicketValidator ticketValidator = new TicketValidator(eventValidator,coordinatesValidator);
    /**
     * Хранилище
     */
    public final Storage storage = new Storage(this, ticketValidator);
    /**
     * Менеджер скриптов
     */
    public final ScriptManager scriptManager = new ScriptManager(5);
    /**
     * Интерактивный поставщик строк
     */
    public final InteractiveSubmitter interactiveSubmitter;
    /**
     * Интерактивный режим работы
     */
    public final InteractiveMode interactiveMode;
    /**
     * Скриптовой режим работы
     */
    public final ScriptMode scriptMode;
    /**
     * Менеджер режимов работы
     */
    public final ModeManager modeManager;
    /**
     * Цепь из обработчика пустых строк и обработчика кавычек
     */
    public final Handler<String> quotationHandler = new EmptinessHandler(new QuotationHandler());
    /**
     * Менеджер команд
     */
    public final CommandManager commandManager;
    /**
     * Менеджер взаимодействия с файлом, содержащим коллекцию
     */
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
