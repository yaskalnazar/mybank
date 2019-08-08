package ua.yaskal.model.dao;

import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.CreditAccount;
import ua.yaskal.model.entity.DepositAccount;

import java.util.List;

public interface AccountDAO extends DAO<Account> {
    List<Account> getAllByOwnerId(long ownerId);
    List<Account> getAllByOwnerIdAndStatus(long ownerId, Account.AccountStatus status);

}
