package ru.jefremov.prog.modes;

import ru.jefremov.prog.interaction.InteractiveSubmitter;

public class InteractiveMode extends Mode{
    public InteractiveMode(Mode next, InteractiveSubmitter submitter) {
        super("user input", next, submitter, true, false);
    }

    @Override
    public Mode finish() {
        return getNextMode();
    }
}
