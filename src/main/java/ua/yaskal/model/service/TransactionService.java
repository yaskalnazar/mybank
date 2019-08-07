package ua.yaskal.model.service;

import ua.yaskal.model.dao.DaoFactory;
import ua.yaskal.model.dao.DepositDAO;
import ua.yaskal.model.dao.TransactionDAO;
import ua.yaskal.model.entity.Transaction;

public class TransactionService {

    public Transaction makeNewTransaction(Transaction transaction){
        TransactionDAO transactionDAO = DaoFactory.getInstance().createTransactionDAO();
        transaction.setId(transactionDAO.addNew(transaction));
        return transaction;
    }
}
