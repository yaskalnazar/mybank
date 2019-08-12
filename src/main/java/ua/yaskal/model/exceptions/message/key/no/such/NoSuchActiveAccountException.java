package ua.yaskal.model.exceptions.message.key.no.such;

public class NoSuchActiveAccountException extends NoSuchException {
    private String messageKey="info.exception.no.such.active.account";

    @Override
    public String getMessageKey() {
        return messageKey;
    }
}
