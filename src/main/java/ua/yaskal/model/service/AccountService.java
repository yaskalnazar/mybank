package ua.yaskal.model.service;

import ua.yaskal.model.dao.DAOFactory;
import ua.yaskal.model.entity.Account;

import java.util.List;

/**
 * This service used for working with account instance.
 *
 * @author Nazar Yaskal
 */
public class AccountService {
    private DAOFactory daoFactory;

    public AccountService(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public void updateAccountStatus(long id, Account.AccountStatus status) {
        daoFactory.createAccountDAO().updateAccountStatus(id, status);
    }


    public List<Account> getAllByOwnerId(long ownerId) {
        return daoFactory.createAccountDAO().getAllByOwnerId(ownerId);
    }

    public List<Account> getAllByOwnerIdAndStatus(long ownerId, Account.AccountStatus status) {
        return daoFactory.createAccountDAO().getAllByOwnerIdAndStatus(ownerId, status);
    }

    public Account getById(long id) {
        return daoFactory.createAccountDAO().getById(id);
    }

    public void setDaoFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
