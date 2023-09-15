package ru.jefremov.prog.commands;

import ru.jefremov.prog.commands.arguments.AbstractArgument;
import ru.jefremov.prog.exceptions.ExitInterruptionException;
import ru.jefremov.prog.exceptions.QuitInterruptionException;
import ru.jefremov.prog.exceptions.command.CommandInterruptionException;
import ru.jefremov.prog.exceptions.command.IllegalCommandArgumentException;
import ru.jefremov.prog.exceptions.command.InvalidCommandArgumentException;
import ru.jefremov.prog.exceptions.command.WrongCommandFormatException;
import ru.jefremov.prog.managers.Administrator;
import ru.jefremov.prog.managers.CommandManager;
import ru.jefremov.prog.managers.Storage;

/**
 * Абстракция команды.
 */
public abstract class AbstractCommand implements Argumentable {
    /**
     * ключевое слово
     */
    public final String word;
    /**
     * парсер аргументов
     */
    public final Parser parser;
    /**
     * хранилище
     */
    public final Storage storage;
    /**
     * администратор
     */
    public final Administrator administrator;
    /**
     * менеджер команд
     */
    public final CommandManager manager;
    /**
     * описание
     */
    public final String description;

    /**
     * Конструктор команды с указанием описания
     * @param word ключевое слово
     * @param manager менеджер команд, к которому она привязана
     * @param description описание
     */
    public AbstractCommand(String word, CommandManager manager, String description) {
        if (word == null || word.isBlank()) {
            throw new IllegalArgumentException("Command should have a command word");
        }
        this.word = word;
        this.parser = new Parser(this);
        if (manager==null) throw new IllegalArgumentException("Command manager must not be null");
        manager.addCommand(this);
        this.manager = manager;
        this.storage = manager.getStorage();
        this.administrator = storage.getAdministrator();
        this.description = (description==null? "" : description);
    }

    /**
     * Конструктор команды без описания
     * @param word ключевое слово
     * @param manager менеджер команд, к которому она привязана
     */
    public AbstractCommand(String word, CommandManager manager) {
        this(word,manager,"");
    }

    /**
     * Метод для запуска команды.
     * @param line строка с аргументами
     * @throws InvalidCommandArgumentException в случае, если аргумент не подходит по формату
     * @throws IllegalCommandArgumentException в случае, если аргумент не подходит по значению
     * @throws WrongCommandFormatException в случае, если нарушен общий формат команды с аргументами
     * @throws CommandInterruptionException в случае, если команда прерывает свой запуск
     * @throws ExitInterruptionException в случае, если программа завершается без сохранения
     * @throws QuitInterruptionException в случае, если пользователь прервал процедуру
     */
    public final void launch(String line) throws InvalidCommandArgumentException, IllegalCommandArgumentException, WrongCommandFormatException, CommandInterruptionException, ExitInterruptionException, QuitInterruptionException {
        parser.parse(line);
        execute();
    }

    protected abstract void execute() throws ExitInterruptionException;

    /**
     * Геттер для ключевого слова
     * @return ключевое слово
     */
    public final String getWord() {
        return word;
    }

    @Override
    public final AbstractCommand referToCommand() {
        return this;
    }

    @Override
    public final void processAccepting(AbstractArgument<?> argument) {
        parser.acceptArgument(argument);
    }

    @Override
    public String toString() {
        return parser.getCommandFormat()+(description.equals("")? "" : "\n--- "+description);
    }
}
