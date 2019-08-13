package ua.yaskal.model.dao;

import ua.yaskal.model.dto.NewDepositContributionDTO;
import ua.yaskal.model.dto.PaginationDTO;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.CreditAccount;
import ua.yaskal.model.entity.DepositAccount;

import java.math.BigDecimal;
import java.util.List;

public interface DepositDAO extends DAO<DepositAccount> {
    List<DepositAccount> getAllByOwnerId(long ownerId);
    List<DepositAccount> getAllByOwnerIdAndStatus(long ownerId, Account.AccountStatus status);
    void updateDepositAmount(long id, BigDecimal amount);
    void updateDepositRate(long id, BigDecimal rate);
    PaginationDTO<DepositAccount> getAllPage(long itemsPerPage, long currentPage);
    void newDepositContribution(NewDepositContributionDTO contributionDTO);

}
