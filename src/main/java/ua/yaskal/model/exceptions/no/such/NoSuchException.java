package ua.yaskal.model.exceptions.no.such;

public abstract class NoSuchException extends RuntimeException {
    public abstract String getMessageKey();
}
