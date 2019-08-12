package ua.yaskal.model.dao;

import ua.yaskal.model.dto.PaginationDTO;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.CreditAccount;
import ua.yaskal.model.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface CreditDAO extends DAO<CreditAccount> {
    List<CreditAccount> getAllByOwnerId(long ownerId);
    List<CreditAccount> getAllByOwnerIdAndStatus(long ownerId, Account.AccountStatus status);
    void increaseAccruedInterestById(long id, BigDecimal accruedInterest);
    void reduceAccruedInterestById(long id, BigDecimal accruedInterest);
    PaginationDTO<CreditAccount> getAllPage(long itemsPerPage, long currentPage);


}
