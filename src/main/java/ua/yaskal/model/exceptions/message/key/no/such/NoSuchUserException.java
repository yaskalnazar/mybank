package ua.yaskal.model.exceptions.message.key.no.such;
//TODO add message work
public class NoSuchUserException extends NoSuchException {
    private String messageKey="info.exception.no.such.user";

    @Override
    public String getMessageKey() {
        return messageKey;
    }
}
