package ru.jefremov.prog.commands.arguments;

import ru.jefremov.prog.commands.AbstractCommand;
import ru.jefremov.prog.commands.Argumentable;
import ru.jefremov.prog.exceptions.QuitInterruptionException;
import ru.jefremov.prog.exceptions.command.*;
import ru.jefremov.prog.interaction.Printer;
import ru.jefremov.prog.managers.Administrator;
import ru.jefremov.prog.managers.ModeManager;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

public abstract class AbstractArgument<T> {
    private T value;
    private final AbstractCommand command;
    public final Administrator administrator;
    public final ModeManager modeManager;
    private final boolean attached;
    public final ArgumentPlacement placement;
    public final String regex;
    public final Pattern pattern;
    public final ArgumentType type;
    public final String name;

    public AbstractArgument(String name, ArgumentPlacement placement, Argumentable argumentable, String regex, ArgumentType type) {
        this.name = ((name==null || name.isBlank()) ? "Argument" : name);
        if (placement==null) throw new IllegalArgumentException("Placement must not be null");
        if (argumentable==null) throw new IllegalArgumentException("Argumentable must not be null");
        this.placement = placement;
        this.regex = ((regex==null || regex.isBlank()) ? type.regex : regex);
        this.pattern = Pattern.compile(this.regex);
        if (type==null) throw new IllegalArgumentException("Argument type must not be null");
        this.type = type;
        this.command = argumentable.referToCommand();
        this.administrator = command.administrator;
        this.modeManager = administrator.modeManager;
        argumentable.acceptArgument(this);
        attached = true;
    }

    public final void form(String text) throws InvalidCommandArgumentException, IllegalCommandArgumentException, CommandInterruptionException, QuitInterruptionException {
        String invalidArgumentDescription = "Argument "+name+" must be represented as "+type.description;
        if (type.textual && text!=null && !text.equals("\\quit") && !pattern.matcher(text).matches()) {
            throw new InvalidCommandArgumentException(invalidArgumentDescription);
        }
        try {
            value = parseValue(text);
        } catch (CommandInterruptionException | QuitInterruptionException e) {
            throw e;
        } catch (Exception e) {
            String exceptionText = invalidArgumentDescription;
            if (e instanceof InvalidCommandArgumentException) exceptionText += "\n"+e.getMessage();
            throw new InvalidCommandArgumentException(exceptionText,e);
        }
        if (!checkValue(value)) throw new IllegalCommandArgumentException(name, text);
    }

    protected abstract T parseValue(String textForm) throws CommandLaunchException, QuitInterruptionException;
    protected boolean checkValue(T value) throws IllegalCommandArgumentException {
        return true;
    }

    public T getValue() {
        T tempValue = value;
        restore();
        return tempValue;
    }

    protected void restore() {
        value = null;
    }

    public AbstractCommand getCommand() {
        return command;
    }

    public boolean isAttached() {
        return attached;
    }

    @Override
    public String toString() {
        return name+":"+type.name;
    }

    public String getInvitation() {
        return name+": ";
    }

    public static void form(ArrayList<AbstractArgument<?>> arguments) throws CommandInterruptionException, QuitInterruptionException {
        if (arguments==null) throw new IllegalArgumentException("List of argument must not be null");
        for (AbstractArgument<?> argument : arguments) {
            while (argument!=null) {
                if (argument.type.complex) {
                    Printer.println("―― "+argument.name+" ――");
                } else {
                    String invitation = argument.getInvitation();
                    Printer.print(invitation);
                    if (invitation.endsWith("\n")) Printer.print("> ");
                }
                try {
                    String line = "";
                    if (!argument.type.complex) {
                        if (argument.modeManager.hasNext()) {
                            try {
                                line = argument.modeManager.next();
                            } catch (NoSuchElementException e) {
                                throw new CommandInterruptionException(argument.modeManager.getEndingMessage());
                            }
                            if (line!=null&&line.equals("\\quit")) throw new QuitInterruptionException("Interactive quit");
                        } else throw new CommandInterruptionException(argument.modeManager.getEndingMessage());
                    }
                    argument.form(line);
                    break;
                } catch (InvalidCommandArgumentException | IllegalCommandArgumentException e) {
                    if (!argument.modeManager.canRespond()) {
                        throw new CommandInterruptionException(e.getMessage());
                    }
                    Printer.println(e.getMessage());
                    argument.restore();
                }
            }
        }
    }
}
