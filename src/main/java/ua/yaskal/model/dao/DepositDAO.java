package ua.yaskal.model.dao;

import ua.yaskal.model.entity.DepositAccount;

import java.util.List;

public interface DepositDAO extends DAO<DepositAccount> {
    List<DepositAccount> getAllByOwnerId(long ownerId);
}
