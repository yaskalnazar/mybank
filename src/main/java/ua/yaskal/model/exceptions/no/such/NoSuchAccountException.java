package ua.yaskal.model.exceptions.no.such;

public class NoSuchAccountException extends NoSuchException {
    private String messageKey="info.exception.no.such.account";

    @Override
    public String getMessageKey() {
        return messageKey;
    }
}
