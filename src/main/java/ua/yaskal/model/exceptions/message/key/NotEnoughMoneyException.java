package ua.yaskal.model.exceptions.message.key;

public class NotEnoughMoneyException extends MessageKeyException {
    private String messageKey="info.exception.not.enough.money";

    public String getMessageKey() {
        return messageKey;
    }
}
