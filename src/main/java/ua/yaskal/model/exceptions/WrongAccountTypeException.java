package ua.yaskal.model.exceptions;

public class WrongAccountTypeException extends RuntimeException {
    private long accountId;

    public long getAccountId() {
        return accountId;
    }

    public WrongAccountTypeException() {
    }

    public WrongAccountTypeException(long accountId) {
        this.accountId = accountId;
    }



}
