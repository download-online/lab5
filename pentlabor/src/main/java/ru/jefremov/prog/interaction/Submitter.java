package ru.jefremov.prog.interaction;

import ru.jefremov.prog.exceptions.QuitInterruptionException;

public interface Submitter<T> {
    boolean hasNext() throws QuitInterruptionException;
    T next();
}
