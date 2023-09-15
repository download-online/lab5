package ru.jefremov.prog.managers;

import ru.jefremov.prog.commands.AbstractCommand;
import ru.jefremov.prog.commands.concrete.*;
import ru.jefremov.prog.exceptions.ExitInterruptionException;
import ru.jefremov.prog.exceptions.QuitInterruptionException;
import ru.jefremov.prog.exceptions.command.CommandLaunchException;
import ru.jefremov.prog.interaction.Printer;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class CommandManager {
    private final HashMap<String, AbstractCommand> commandMap = new HashMap<>();
    private final Storage storage;
    private final Administrator administrator;
    private final ArrayList<HistoryRecord> history = new ArrayList<>();
    private final ArrayList<AbstractCommand> commandList = new ArrayList<>();

    public CommandManager(Storage storage) {
        if (storage==null) throw new IllegalArgumentException("Storage must not be null");
        this.storage = storage;
        this.administrator = storage.getAdministrator();

        String addDescription = "Добавить новый элемент в коллекцию";
        String updateDescription = "Обновить значение элемента коллекции, id которого равен заданному";
        String showDescription = "Вывести все элементы коллекции";
        String executeScriptDescription = "Исполнить скрипт из указанного файла.";
        String removeByIdDescription = "Удалить элемент из коллекции по его id";
        String historyDescription = "Вывести последние 5 команд";
        String clearDescription = "Очистить коллекцию";
        String exitDescription = "Завершить программу (без сохранения в файл)";
        String helpDescription = "Вывести справку по доступным командам";
        String infoDescription = "Вывести информацию о коллекции";
        String addIfMinDescription = "Добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
        String removeLowerDescription = "Удалить из коллекции все элементы, меньшие, чем заданный";
        String filterStartsWithCommentDescription = "Вывести элементы, комментарии которых начинаются с заданной подстроки";
        String filterLessThanEventDescription = "Вывести элементы, значение поля event которых меньше заданного";
        String printDescendingDescription = "Вывести элементы коллекции в порядке убывания";
        String saveDescription = "Сохранить коллекцию в файл";

        new AddCommand("add",this, addDescription);
        new UpdateCommand("update",this, updateDescription);
        new ShowCommand("show",this,showDescription);
        new ExecuteScriptCommand("execute_script", this,executeScriptDescription);
        new RemoveByIdCommand("remove_by_id", this,removeByIdDescription);
        new HistoryCommand("history", this,historyDescription);
        new ClearCommand("clear",this,clearDescription);
        new ExitCommand("exit",this,exitDescription);
        new HelpCommand("help",this,helpDescription);
        new InfoCommand("info",this,infoDescription);
        new AddIfMinCommand("add_if_min", this, addIfMinDescription);
        new RemoveLowerCommand("remove_lower", this, removeLowerDescription);
        new FilterStartsWithCommentCommand("filter_starts_with_comment", this, filterStartsWithCommentDescription);
        new FilterLessThanEventCommand("filter_less_than_event", this, filterLessThanEventDescription);
        new PrintDescendingCommand("print_descending",this,printDescendingDescription);
        new SaveCommand("save",this,saveDescription);
    }

    public void addCommand(AbstractCommand command) {
        if (command==null) {
            throw new IllegalArgumentException("Command must not be null");
        }
        commandMap.put(command.getWord(),command);
        commandList.add(command);
        commandList.sort(Comparator.comparing(c -> c.word));
    }

    public Storage getStorage() {
        return storage;
    }

    public void launchCommand(String word, String line) throws CommandLaunchException, ExitInterruptionException, QuitInterruptionException {
        AbstractCommand command = commandMap.get(word);
        if (command==null) {
            throw new CommandLaunchException("Command not found: "+word);
        }
        command.launch(line);
        history.add(new HistoryRecord(word, line));
    }

    private static class HistoryRecord {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        private final String word;
        private final String line;
        private final LocalTime launched;
        private final String launchedString;
        private HistoryRecord(String word, String line) {
            this.word = word;
            this.line = line;
            this.launched = LocalTime.now();
            this.launchedString = launched.format(formatter);
        }

        @Override
        public String toString() {
            return launchedString+" "+word;
        }
    }

    public void printHistory() {
        if (history.size()==0) {
            Printer.println("The history does not contain any successfully executed commands");
        } else {
            var lastRecords = history.subList(Math.max(history.size() - 5, 0), history.size());
            lastRecords.forEach(Printer::println);
        }
    }

    public void printHelp() {
        Printer.println("Help for available commands:");
        commandList.forEach(Printer::println);
    }
}
