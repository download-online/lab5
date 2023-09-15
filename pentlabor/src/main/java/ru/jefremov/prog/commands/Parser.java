package ru.jefremov.prog.commands;

import ru.jefremov.prog.commands.arguments.AbstractArgument;
import ru.jefremov.prog.commands.arguments.ArgumentPlacement;
import ru.jefremov.prog.commands.arguments.ArgumentType;
import ru.jefremov.prog.exceptions.QuitInterruptionException;
import ru.jefremov.prog.exceptions.command.*;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public final ArrayList<AbstractArgument<?>> newlineArguments = new ArrayList<>();
    public final ArrayList<AbstractArgument<?>> inlineArguments = new ArrayList<>();
    public final AbstractCommand command;
    private boolean initialised;
    private int currentArgumentIndex = 0;
    private Pattern pattern;
    private String regex;

    public Parser(AbstractCommand command) {
        if (command==null) throw new IllegalArgumentException("Command must not be null");
        this.command = command;
        regex = "^"+command.getWord();
    }

    protected boolean acceptArgument(AbstractArgument<?> argument) {
        if (initialised) throw new IllegalStateException("Trying to add argument to initialised parser");
        if (argument==null) throw new IllegalArgumentException("Argument must not be null");
        if (argument.placement == ArgumentPlacement.INLINE) {
            if (argument.type == ArgumentType.OBJECT) throw new IllegalArgumentException("Inline object argument");
            String groupName = "arg"+(currentArgumentIndex++);
            regex += " (?<" + groupName + ">" + argument.regex + ")";
            return inlineArguments.add(argument);
        }
        return newlineArguments.add(argument);
    }

    protected void parse(String line) throws InvalidCommandArgumentException, IllegalCommandArgumentException, WrongCommandFormatException, CommandInterruptionException, QuitInterruptionException {
        if (!initialised) {
            regex+="$";
            pattern = Pattern.compile(regex);
            initialised = true;
        }
        runParsingProcess(line);
    }

    protected void runParsingProcess(String line) throws InvalidCommandArgumentException, IllegalCommandArgumentException, CommandInterruptionException, WrongCommandFormatException, QuitInterruptionException {
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
            int argumentIndex = 0;
            for (AbstractArgument<?> argument : inlineArguments) {
                String groupName = "arg"+(argumentIndex++);
                String parsedValue = matcher.group(groupName);
                argument.form(parsedValue);
            }
        } else {
            throw new WrongCommandFormatException("Wrong command format. Try:\n"+getCommandFormat());
        }
        AbstractArgument.form(newlineArguments);
    }

    public String getCommandFormat() {
        StringBuilder commandFormat = new StringBuilder(command.word);
        for (AbstractArgument<?> argument:
             inlineArguments) {
            commandFormat.append(" ").append(argument.toString());
        }
        return commandFormat.toString();
    }
}
