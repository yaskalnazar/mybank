package ua.yaskal.model.exceptions.no.such;

public abstract class NoSuchException extends RuntimeException {
    public abstract String getMessageKey();

    @Override
    public String getMessage() {
        return getMessageKey();
    }
}
