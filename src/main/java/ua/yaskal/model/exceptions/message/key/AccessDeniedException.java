package ua.yaskal.model.exceptions.message.key;

public class AccessDeniedException extends MessageKeyException {
    private String messageKey="page.message.access.denied";

    @Override
    public String getMessageKey() {
        return messageKey;
    }
}
