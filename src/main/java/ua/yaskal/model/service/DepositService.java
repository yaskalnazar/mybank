package ua.yaskal.model.service;

import ua.yaskal.model.dao.DAOFactory;
import ua.yaskal.model.dto.DepositDTO;
import ua.yaskal.model.dto.NewDepositContributionDTO;
import ua.yaskal.model.dto.PaginationDTO;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.DepositAccount;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * This service used for working with deposit accounts instance.
 *
 * @author Nazar Yaskal
 */
public class DepositService {
    private DAOFactory daoFactory;
    private static final long NUMBER_YEARS_OF_CARD_VALID = 5;


    public DepositService(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public DepositAccount openNewDeposit(DepositDTO depositDTO) {
        DepositAccount depositAccount = DepositAccount.getBuilder()
                .setBalance(BigDecimal.ZERO)
                .setClosingDate(LocalDate.now().plusYears(NUMBER_YEARS_OF_CARD_VALID))
                .setOwnerId(depositDTO.getOwnerId())
                .setAccountStatus(Account.AccountStatus.ACTIVE)
                .setDepositAmount(depositDTO.getDepositAmount())
                .setDepositRate(depositDTO.getDepositRate())
                .setDepositEndDate(LocalDate.now().plusMonths(depositDTO.getMonthsAmount()))
                .build();

        depositAccount.setId(daoFactory.createDepositDAO().addNew(depositAccount));

        return depositAccount;
    }

    public List<DepositAccount> getAll() {
        return daoFactory.createDepositDAO().getAll();
    }

    public List<DepositAccount> getAllByOwnerId(long ownerId) {
        return daoFactory.createDepositDAO().getAllByOwnerId(ownerId);
    }

    public List<DepositAccount> getAllByOwnerIdAndStatus(long ownerId, Account.AccountStatus status) {
        return daoFactory.createDepositDAO().getAllByOwnerIdAndStatus(ownerId, status);
    }

    public DepositAccount getById(long id) {
        return daoFactory.createDepositDAO().getById(id);
    }

    public PaginationDTO<DepositAccount> getAllPage(long itemsPerPage, long currentPage) {
        return daoFactory.createDepositDAO().getAllPage(itemsPerPage, currentPage);
    }

    public void setDaoFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public void newDepositContribution(NewDepositContributionDTO contributionDTO) {
        daoFactory.createDepositDAO().newDepositContribution(contributionDTO);
    }
}
