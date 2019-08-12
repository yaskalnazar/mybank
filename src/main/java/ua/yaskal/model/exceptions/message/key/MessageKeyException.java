package ua.yaskal.model.exceptions.message.key;

public abstract class MessageKeyException extends RuntimeException{
    public abstract String getMessageKey();

    @Override
    public String getMessage() {
        return getMessageKey();
    }
}
