package ru.jefremov.prog.models.validation;

import ru.jefremov.prog.models.Event;
import ru.jefremov.prog.models.EventType;

public abstract class AbstractEventValidator {
    public abstract String reviewId(long id);
    public abstract String reviewName(String name);
    public abstract String reviewTicketsCount(Long ticketsCount);
    public abstract String reviewEventType(EventType eventType);
    public String reviewEvent(Event event) {
        if (event==null) {
            return "Null event at the review";
        }
        String review1 = reviewId(event.getId());
        if (review1!=null) {
            return review1;
        }
        String review2 = reviewName(event.getName());
        if (review2!=null) {
            return review2;
        }
        String review3 = reviewTicketsCount(event.getTicketsCount());
        if (review3!=null) {
            return review3;
        }
        return reviewEventType(event.getEventType());
    }
    public abstract boolean checkId(long id);
    public abstract boolean checkName(String name);
    public abstract boolean checkTicketsCount(Long ticketsCount);
    public abstract boolean checkEventType(EventType eventType);
    public boolean checkEvent(Event event) {
        if (event==null) {
            return false;
        }
        return checkId(event.getId()) & checkName(event.getName()) &
                checkTicketsCount(event.getTicketsCount()) &
                checkEventType(event.getEventType());
    }
}
