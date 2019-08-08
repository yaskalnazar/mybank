package ua.yaskal.model.exceptions.no.such;
//TODO add message work
public class NoSuchActiveAccountException extends NoSuchException {
    private String messageKey="info.exception.no.such.active.account";

    @Override
    public String getMessageKey() {
        return messageKey;
    }
}
