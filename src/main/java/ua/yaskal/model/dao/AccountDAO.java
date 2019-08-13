package ua.yaskal.model.dao;

import ua.yaskal.model.entity.Account;

import java.util.List;

/**
 * This is extension of {@link DAO} template for {@link Account} entity
 *
 * @author Nazar Yaskal
 * @see DAO
 * @see Account
 */
public interface AccountDAO extends DAO<Account> {
    List<Account> getAllByOwnerId(long ownerId);
    List<Account> getAllByOwnerIdAndStatus(long ownerId, Account.AccountStatus status);
    List<Account> getAllByStatus(Account.AccountStatus status);
    void updateAccountStatus(long id, Account.AccountStatus status);

}
