package ru.jefremov.prog.models.validation;

public class CoordinatesValidator extends AbstractCoordinatesValidator{
    @Override
    public String reviewX(int x) {
        if (x>-915) {
            return null;
        }
        return "X coordinate should be greater than -915";
    }

    @Override
    public String reviewY(Double y) {
        if (y!=null) {
            return null;
        }
        return "Y should represent at least some value";
    }

    @Override
    public boolean checkX(int x) {
        return (x>-915);
    }

    @Override
    public boolean checkY(Double y) {
        return (y!=null);
    }


}
