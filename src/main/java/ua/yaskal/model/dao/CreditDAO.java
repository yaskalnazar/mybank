package ua.yaskal.model.dao;

import ua.yaskal.model.entity.CreditAccount;
import ua.yaskal.model.entity.DepositAccount;

import java.util.List;

public interface CreditDAO extends DAO<CreditAccount> {
    List<CreditAccount> getAllByOwnerId(long ownerId);
}
