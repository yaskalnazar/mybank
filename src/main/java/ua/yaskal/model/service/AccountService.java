package ua.yaskal.model.service;

import ua.yaskal.model.dao.AccountDAO;
import ua.yaskal.model.dao.CreditDAO;
import ua.yaskal.model.dao.DaoFactory;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.DepositAccount;

import java.util.List;

public class AccountService {

    public List<Account> getAllByOwnerId(long ownerId){
        return DaoFactory.getInstance().createAccountDAO().getAllByOwnerId(ownerId);
    }

    public List<Account> getAllByOwnerIdAndStatus(long ownerId, Account.AccountStatus status){
        return DaoFactory.getInstance().createAccountDAO().getAllByOwnerIdAndStatus(ownerId, status);
    }
}
