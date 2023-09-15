package ru.jefremov.prog.interaction;

import ru.jefremov.prog.exceptions.SavedCollectionInteractionException;
import ru.jefremov.prog.models.Ticket;

import java.util.LinkedHashSet;

public interface SavedCollectionInteracting {
    LinkedHashSet<Ticket> loadCollection() throws SavedCollectionInteractionException;
    void saveCollection(LinkedHashSet<Ticket> collection) throws SavedCollectionInteractionException;
}
