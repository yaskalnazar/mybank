package ua.yaskal.model.dao;

import ua.yaskal.model.entity.Transaction;

import java.util.List;

public interface TransactionDAO extends DAO<Transaction> {
    List<Transaction> getAllByReceiverId(long id);
    List<Transaction> getAllBySenderId(long id);
    List<Transaction> getAllByAccountId(long id);

}
