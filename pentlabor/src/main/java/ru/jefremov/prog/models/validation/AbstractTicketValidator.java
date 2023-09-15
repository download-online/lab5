package ru.jefremov.prog.models.validation;

import ru.jefremov.prog.models.*;
import java.time.LocalDate;
import static java.util.Objects.requireNonNull;

public abstract class AbstractTicketValidator {
    private final AbstractEventValidator eventValidator;
    private final AbstractCoordinatesValidator coordinatesValidator;

    public AbstractTicketValidator(AbstractEventValidator eventValidator, AbstractCoordinatesValidator coordinatesValidator) {
        this.eventValidator = requireNonNull(eventValidator, "event validator must not be null");
        this.coordinatesValidator = requireNonNull(coordinatesValidator,"coordinates validator must not be null");
    }

    public abstract String reviewId(int id);
    public abstract String reviewName(String name);
    public String reviewCoordinates(Coordinates coordinates) {
        return coordinatesValidator.reviewCoordinates(coordinates);
    }
    public abstract String reviewCreationDate(LocalDate creationDate);
    public abstract String reviewPrice(Double price);
    public abstract String reviewDiscount(double discount);
    public abstract String reviewComment(String comment);
    public abstract String reviewType(TicketType type);
    public String reviewEvent(Event event) {
        return eventValidator.reviewEvent(event);
    }
    public String reviewTicket(Ticket ticket) {
        if (ticket==null) {
            return "Null ticket at the review";
        }
        String review1 = reviewId(ticket.getId());
        if (review1!=null) {
            return review1;
        }
        String review2 = reviewName(ticket.getName());
        if (review2!=null) {
            return review2;
        }
        String review3 = reviewCoordinates(ticket.getCoordinates());
        if (review3!=null) {
            return review3;
        }
        String review4 = reviewCreationDate(ticket.getCreationDate());
        if (review4!=null) {
            return review4;
        }
        String review5 = reviewPrice(ticket.getPrice());
        if (review5!=null) {
            return review5;
        }
        String review6 = reviewDiscount(ticket.getDiscount());
        if (review6!=null) {
            return review6;
        }
        String review7 = reviewComment(ticket.getComment());
        if (review7!=null) {
            return review7;
        }
        String review8 = reviewType(ticket.getType());
        if (review8!=null) {
            return review8;
        }
        return reviewEvent(ticket.getEvent());
    }

    public abstract boolean checkId(int id);
    public abstract boolean checkName(String name);
    public boolean checkCoordinates(Coordinates coordinates) {
        return coordinatesValidator.checkCoordinates(coordinates);
    }
    public abstract boolean checkCreationDate(LocalDate creationDate);
    public abstract boolean checkPrice(Double price);
    public abstract boolean checkDiscount(double discount);
    public abstract boolean checkComment(String comment);
    public abstract boolean checkType(TicketType type);
    public boolean checkEvent(Event event) {
        return eventValidator.checkEvent(event);
    }
    public boolean checkTicket(Ticket ticket) {
        if (ticket==null) {
            return false;
        }
        return checkId(ticket.getId()) &
                checkName(ticket.getName()) &
                checkCoordinates(ticket.getCoordinates()) &
                checkCreationDate(ticket.getCreationDate()) &
                checkPrice(ticket.getPrice()) &
                checkDiscount(ticket.getDiscount()) &
                checkComment(ticket.getComment()) &
                checkType(ticket.getType()) &
                checkEvent(ticket.getEvent());
    }
}
