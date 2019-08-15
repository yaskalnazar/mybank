package ua.yaskal.model.service;

import ua.yaskal.model.dao.DAOFactory;
import ua.yaskal.model.dto.PaginationDTO;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.CreditAccount;
import ua.yaskal.model.entity.CreditRequest;
import ua.yaskal.model.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * This service used for working with credit accounts instance.
 *
 * @author Nazar Yaskal
 */
public class CreditService {
    private DAOFactory daoFactory;
    private static final long NUMBER_YEARS_OF_CARD_VALID = 5;


    public CreditService(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public List<CreditAccount> getAll() {
        return daoFactory.createCreditDAO().getAll();
    }

    public List<CreditAccount> getAllByOwnerId(long ownerId) {
        return daoFactory.createCreditDAO().getAllByOwnerId(ownerId);
    }

    public CreditAccount addNew(CreditRequest creditRequest) {
        CreditAccount creditAccount = CreditAccount.getBuilder()
                .setBalance(BigDecimal.ZERO)
                .setClosingDate(LocalDate.now().plusYears(NUMBER_YEARS_OF_CARD_VALID))
                .setOwnerId(creditRequest.getApplicantId())
                .setAccountStatus(Account.AccountStatus.ACTIVE)
                .setCreditLimit(creditRequest.getCreditLimit())
                .setCreditRate(creditRequest.getCreditRate())
                .setAccruedInterest(BigDecimal.ZERO)
                .build();

        creditAccount.setId(daoFactory.createCreditDAO().addNew(creditAccount));
        return creditAccount;

    }

    public List<CreditAccount> getAllByOwnerIdAndStatus(long ownerId, Account.AccountStatus status) {
        return daoFactory.createCreditDAO().getAllByOwnerIdAndStatus(ownerId, status);
    }

    public CreditAccount getById(long id) {
        return daoFactory.createCreditDAO().getById(id);
    }

    public void setDaoFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public void payAccruedInterest(Transaction transaction, long receiverCreditId){
        daoFactory.createCreditDAO().payAccruedInterest(transaction, receiverCreditId);
    }

    public void increaseAccruedInterestById(long id, BigDecimal accruedInterest) {
        daoFactory.createCreditDAO().reduceAccruedInterestById(id, accruedInterest);
    }

    public void reduceAccruedInterestById(long id, BigDecimal accruedInterest) {
        daoFactory.createCreditDAO().reduceAccruedInterestById(id, accruedInterest);
    }

    public PaginationDTO<CreditAccount> getAllPage(long itemsPerPage, long currentPage) {
        return daoFactory.createCreditDAO().getAllPage(itemsPerPage,currentPage);
    }


}
