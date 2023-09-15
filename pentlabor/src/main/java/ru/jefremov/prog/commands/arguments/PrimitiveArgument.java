package ru.jefremov.prog.commands.arguments;

import ru.jefremov.prog.commands.Argumentable;

/**
 * Аргумент произвольного типа, отвечающий за не-составные поля.
 * @param <T> тип значения аргумента.
 */
public abstract class PrimitiveArgument<T> extends AbstractArgument<T> {
    public PrimitiveArgument(String name, ArgumentPlacement placement, Argumentable argumentable, String regex, ArgumentType type) {
        super(name, placement, argumentable, regex, type);
    }
}
