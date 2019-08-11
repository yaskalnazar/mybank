package ua.yaskal.model.service;

import ua.yaskal.model.dao.DAOFactory;
import ua.yaskal.model.dao.TransactionDAO;
import ua.yaskal.model.entity.Transaction;

import java.util.List;

public class TransactionService {

    public Transaction makeNewTransaction(Transaction transaction){
        TransactionDAO transactionDAO = DAOFactory.getInstance().createTransactionDAO();
        transaction.setId(transactionDAO.addNew(transaction));
        return transaction;
    }

    public List<Transaction> getAllByReceiverId(long id){
        return DAOFactory.getInstance().createTransactionDAO().getAllByReceiverId(id);
    }
    public List<Transaction> getAllBySenderId(long id){
        return DAOFactory.getInstance().createTransactionDAO().getAllBySenderId(id);
    }

    public List<Transaction> getAllByAccountId(long id){
        return DAOFactory.getInstance().createTransactionDAO().getAllByAccountId(id);
    }

}
