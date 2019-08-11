package ua.yaskal.model.service;

import ua.yaskal.model.dao.DAOFactory;
import ua.yaskal.model.entity.Account;

import java.util.List;

public class AccountService {

    public List<Account> getAllByOwnerId(long ownerId){
        return DAOFactory.getInstance().createAccountDAO().getAllByOwnerId(ownerId);
    }

    public List<Account> getAllByOwnerIdAndStatus(long ownerId, Account.AccountStatus status){
        return DAOFactory.getInstance().createAccountDAO().getAllByOwnerIdAndStatus(ownerId, status);
    }

    public Account getById(long id){
        return DAOFactory.getInstance().createAccountDAO().getById(id);
    }
}
