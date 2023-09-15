package ru.jefremov.prog.managers;

import ru.jefremov.prog.interaction.Printer;
import ru.jefremov.prog.models.Event;
import ru.jefremov.prog.models.Ticket;
import ru.jefremov.prog.models.validation.AbstractTicketValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;

public class Storage {
    private LinkedHashSet<Ticket> collection;
    private final AbstractTicketValidator ticketValidator;
    private final Administrator administrator;
    private LocalDate initDate;

    public Storage(Administrator administrator, AbstractTicketValidator ticketValidator) {
        if (administrator==null) throw new IllegalArgumentException("Administrator must not be null");
        this.administrator = administrator;
        if (ticketValidator==null) throw new IllegalArgumentException("Ticket validator must not be null");
        this.ticketValidator = ticketValidator;
        this.collection = new LinkedHashSet<>();
        initDate = LocalDate.now();
    }

    public LinkedHashSet<Ticket> getCollectionCopy() {
        return new LinkedHashSet<>(collection);
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public boolean loadCollection(LinkedHashSet<Ticket> collection) {
        if (collection==null) {
            throw new IllegalArgumentException("collection must not be null");
        }
        if (checkCollection(collection)) {
            this.collection = new LinkedHashSet<>(collection);
            initDate = LocalDate.now();
            return true;
        }
        return false;
    }

    public boolean checkCollection(LinkedHashSet<Ticket> collection) {
        if (collection==null) {
            return false;
        }
        for (Ticket ticket:
             collection) {
            if (!ticketValidator.checkTicket(ticket)) {
                return false;
            }
        }
        return true;
    }

    public String reviewCollection(LinkedHashSet<Ticket> collection) {
        if (collection==null) {
            return null;
        }
        int c = 0;
        for (Ticket ticket:
                collection) {
            String result = ticketValidator.reviewTicket(ticket);
            if (!(result==null)) {
                if (ticket==null) return "Null ticket at "+c+" position";
                return "At ticket #"+ticket.getId()+": "+result;
            }
            c+=1;
        }
        return null;
    }

    public boolean addTicket(Ticket ticket) {
        if (ticketValidator.checkTicket(ticket)) {
            return collection.add(ticket);
        }
        return false;
    }

    private Ticket getById(int id) {
        for (Ticket ticket:
             collection) {
            if (ticket.getId()==id) {
                return ticket;
            }
        }
        return null;
    }

    public boolean hasId(int id) {
        return (getById(id)!=null);
    }

    public boolean updateById(int id, Ticket other) {
        Ticket ticket = getById(id);
        if (ticket==null) {
            return false;
        }
        if (ticketValidator.checkTicket(other)) {
            collection.remove(ticket);
            ticket.update(other);
            return collection.add(ticket);
        }
        return false;
    }

    public boolean removeById(int id) {
        Ticket ticket = getById(id);
        if (ticket==null) {
            return false;
        }
        return collection.remove(ticket);
    }

    public void clear() {
        collection.clear();
    }

    public boolean show() {
        if (collection.isEmpty()) return false;
        for (Ticket ticket:
             collection) {
            Printer.println(ticket);
        }
        return true;
    }

    public int size() {
        return collection.size();
    }

    public void printInfo() {
        Printer.println(collection.getClass().getName()+" collection with "+size()+" elements.");
        Printer.println("Initialisation date: "+ initDate);
        Printer.println("The collection is linked to a file: "+ administrator.collectionFile);
    }

    public boolean addIfMin(Ticket ticket) {
        if (collection.isEmpty() || Collections.min(collection).compareTo(ticket)>0) {
            return addTicket(ticket);
        }
        return false;
    }

    public int removeLower(Ticket ticket) {
        int sizeBefore = collection.size();
        collection.removeIf(ticket1 -> ticket1.compareTo(ticket)<0);
        int sizeAfter = collection.size();
        return sizeAfter-sizeBefore;
    }

    public boolean printFilterStartsWithComment(String comment) {
        if (!ticketValidator.checkComment(comment)) return false;
        boolean appliedAny = false;
        for (Ticket ticket:
             collection) {
            if (ticket.getComment()!=null && ticket.getComment().startsWith(comment)) {
                Printer.println(ticket);
                appliedAny = true;
            }
        }
        return appliedAny;
    }

    public boolean printFilterLessThanEvent(Event event) {
        if (!ticketValidator.checkEvent(event)) return false;
        boolean appliedAny = false;
        for (Ticket ticket:
                collection) {
            if (ticket.getEvent().compareTo(event)<0) {
                Printer.println(ticket);
                appliedAny = true;
            }
        }
        return appliedAny;
    }

    public boolean printDescending() {
        if (collection.isEmpty()) return false;
        var list = new ArrayList<>(collection);
        list.sort(null);
        Collections.reverse(list);
        list.forEach(Printer::println);
        return true;
    }
}
