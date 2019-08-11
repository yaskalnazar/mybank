package ua.yaskal.model.service;

import ua.yaskal.model.dao.DAOFactory;
import ua.yaskal.model.entity.Transaction;

import java.util.List;

public class TransactionService {
    private DAOFactory daoFactory;

    public TransactionService(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Transaction makeNewTransaction(Transaction transaction) {
        transaction.setId(daoFactory.createTransactionDAO().addNew(transaction));
        return transaction;
    }

    public List<Transaction> getAllByReceiverId(long id) {
        return daoFactory.createTransactionDAO().getAllByReceiverId(id);
    }

    public List<Transaction> getAllBySenderId(long id) {
        return daoFactory.createTransactionDAO().getAllBySenderId(id);
    }

    public List<Transaction> getAllByAccountId(long id) {
        return daoFactory.createTransactionDAO().getAllByAccountId(id);
    }

    public void setDaoFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
