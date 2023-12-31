package ru.jefremov.prog.modes;

import ru.jefremov.prog.exceptions.QuitInterruptionException;
import ru.jefremov.prog.interaction.Submitter;

import java.util.NoSuchElementException;

public abstract class Mode implements Submitter<String> {
    private final Mode next;
    public final String name;
    private final Submitter<String> submitter;
    private final boolean responsive;
    private final boolean autoSwitching;

    public Mode(String name, Mode next, Submitter<String> submitter, boolean responsive, boolean autoSwitching) {
        this.name = ((name==null||name.isBlank())? "mode" : name);
        this.next = next;
        if (submitter==null) {
            throw new IllegalArgumentException("Submitter must not be null");
        }
        this.submitter = submitter;
        this.responsive = responsive;
        this.autoSwitching = autoSwitching;
        if ((next==null) & autoSwitching) {
            throw new IllegalArgumentException("No next mode specified, but auto-switching turned on");
        }
    }

    public Mode getNextMode() {
        return next;
    }

    public boolean hasNext() throws QuitInterruptionException {
        return submitter.hasNext();
    }

    public String next() {
        try {
            return submitter.next();
        } catch (NoSuchElementException e) {
            finish();
            return null;
        }
    }

    public boolean isResponsive() {
        return responsive;
    }

    public boolean isAutoSwitching() {
        return autoSwitching;
    }

    public abstract Mode finish();

    public Submitter<String> getSubmitter() {
        return submitter;
    }
}
