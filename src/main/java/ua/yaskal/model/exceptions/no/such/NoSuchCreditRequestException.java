package ua.yaskal.model.exceptions.no.such;

public class NoSuchCreditRequestException extends NoSuchException {
    private String messageKey="info.exception.no.such.credit.request";
    @Override
    public String getMessageKey() {
        return messageKey;
    }
}
