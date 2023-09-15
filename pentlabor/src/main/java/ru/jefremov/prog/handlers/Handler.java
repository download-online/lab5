package ru.jefremov.prog.handlers;

public abstract class Handler<T> {
    private Handler<T> next;

    public Handler() {

    }

    public Handler(Handler<T> next) {
        this.next = next;
    }

    protected abstract T apply(T t);
    public final T handle(T t) {
        T newValue = apply(t);
        if (next==null) {
            return newValue;
        }
        return next.handle(newValue);
    }
}
