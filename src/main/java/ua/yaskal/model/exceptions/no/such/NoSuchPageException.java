package ua.yaskal.model.exceptions.no.such;

public class NoSuchPageException extends NoSuchException {
    private String messageKey="info.exception.no.such.page";

    @Override
    public String getMessageKey() {
        return messageKey;
    }
}
