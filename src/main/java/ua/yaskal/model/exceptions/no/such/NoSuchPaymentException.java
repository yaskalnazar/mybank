package ua.yaskal.model.exceptions.no.such;

public class NoSuchPaymentException extends NoSuchException {
    private String messageKey="info.exception.no.such.payment";

    @Override
    public String getMessageKey() {
        return messageKey;
    }
}
