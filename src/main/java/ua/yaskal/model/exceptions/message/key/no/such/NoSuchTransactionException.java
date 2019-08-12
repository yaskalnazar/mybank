package ua.yaskal.model.exceptions.message.key.no.such;

public class NoSuchTransactionException extends NoSuchException {
    private String messageKey="info.exception.no.such.transaction";

    @Override
    public String getMessageKey() {
        return messageKey;
    }
}
