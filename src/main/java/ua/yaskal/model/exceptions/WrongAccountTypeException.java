package ua.yaskal.model.exceptions;

/**
 * This exception is thrown while trying map account from database with the unforeseen account type
 *
 * @author Nazar Yaskal
 * @see ua.yaskal.model.dao.mappers.jdbc.JDBCAccountMapper;
 */
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
