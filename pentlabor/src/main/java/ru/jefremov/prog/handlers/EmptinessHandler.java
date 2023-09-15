package ru.jefremov.prog.handlers;

public class EmptinessHandler extends Handler<String>{

    public EmptinessHandler() {
        super(null);
    }

    public EmptinessHandler(Handler<String> next) {
        super(next);
    }

    @Override
    protected String apply(String s) {
        if (s==null || s.isEmpty()) {
            return null;
        }

        return s.trim();
    }
}
