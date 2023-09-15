package ru.jefremov.prog.models.validation;

import ru.jefremov.prog.models.Coordinates;

public abstract class AbstractCoordinatesValidator {
    public abstract String reviewX(int x);
    public abstract String reviewY(Double y);
    public String reviewCoordinates(Coordinates coordinates) {
        if (coordinates==null) {
            return "Null coordinates at the review";
        }
        String review1 = reviewX(coordinates.getX());
        if (review1!=null) {
            return review1;
        }
        return reviewY(coordinates.getY());
    }

    public abstract boolean checkX(int x);
    public abstract boolean checkY(Double y);
    public boolean checkCoordinates(Coordinates coordinates) {
        if (coordinates==null) {
            return false;
        }
        return checkX(coordinates.getX()) & checkY(coordinates.getY());
    }
}
