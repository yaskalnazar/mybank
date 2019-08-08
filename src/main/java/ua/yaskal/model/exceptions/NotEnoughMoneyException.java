package ua.yaskal.model.exceptions;

public class NotEnoughMoneyException extends RuntimeException {
    private String messageKey="info.exception.not.enough.money";

    public String getMessageKey() {
        return messageKey;
    }
}
