package ua.yaskal.model.exceptions.message.key;

import ua.yaskal.model.exceptions.message.key.MessageKeyException;

public class DepositAlreadyActiveException extends MessageKeyException {
    private String messageKey="info.exception.deposit.already.active";
    @Override
    public String getMessageKey() {
        return messageKey;
    }
}
