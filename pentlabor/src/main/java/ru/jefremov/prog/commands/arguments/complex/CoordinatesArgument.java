package ru.jefremov.prog.commands.arguments.complex;

import ru.jefremov.prog.commands.Argumentable;
import ru.jefremov.prog.commands.arguments.ArgumentPlacement;
import ru.jefremov.prog.commands.arguments.ComplexArgument;
import ru.jefremov.prog.commands.arguments.concrete.CoordinatesXArgument;
import ru.jefremov.prog.commands.arguments.concrete.CoordinatesYArgument;
import ru.jefremov.prog.commands.arguments.primitive.DoubleArgument;
import ru.jefremov.prog.commands.arguments.primitive.IntegerArgument;
import ru.jefremov.prog.exceptions.command.IllegalCommandArgumentException;
import ru.jefremov.prog.models.Coordinates;

public class CoordinatesArgument extends ComplexArgument<Coordinates> {
    private final IntegerArgument arg1 = new CoordinatesXArgument("X", ArgumentPlacement.NEWLINE,this,null);
    private final DoubleArgument arg2 = new CoordinatesYArgument("Y",ArgumentPlacement.NEWLINE,this,null);

    public CoordinatesArgument(String name, Argumentable argumentable) {
        super(name, argumentable);
    }

    @Override
    protected boolean checkValue(Coordinates value) throws IllegalCommandArgumentException {
        String comment = administrator.coordinatesValidator.reviewCoordinates(value);
        if (comment!=null) throw new IllegalCommandArgumentException(name, comment);
        return true;
    }

    @Override
    public Coordinates formComplexValue() {
        return new Coordinates(arg1.getValue(),arg2.getValue());
    }
}
