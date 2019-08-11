package ua.yaskal.model.service;

import ua.yaskal.model.dao.DAOFactory;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.CreditAccount;
import ua.yaskal.model.entity.CreditRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CreditService {
    private DAOFactory daoFactory;

    public CreditService(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public List<CreditAccount> getAll(){
        return daoFactory.createCreditDAO().getAll();
    }

    public List<CreditAccount> getAllByOwnerId(long ownerId){
        return daoFactory.createCreditDAO().getAllByOwnerId(ownerId);
    }

    public CreditAccount addNew(CreditRequest creditRequest){
        CreditAccount creditAccount = CreditAccount.getBuilder()
                .setBalance(new BigDecimal(0))
                .setClosingDate(LocalDate.now().plusYears(5))
                .setOwnerId(creditRequest.getApplicantId())
                .setAccountStatus(Account.AccountStatus.ACTIVE)
                .setCreditLimit(creditRequest.getCreditLimit())
                .setCreditRate(creditRequest.getCreditRate())
                .setAccruedInterest(new BigDecimal(0))
                .build();

        creditAccount.setId(daoFactory.createCreditDAO().addNew(creditAccount));
        return creditAccount;

    }

    public List<CreditAccount> getAllByOwnerIdAndStatus(long ownerId, Account.AccountStatus status){
        return daoFactory.createCreditDAO().getAllByOwnerIdAndStatus(ownerId, status);
    }

    public CreditAccount getById(long id){
        return daoFactory.createCreditDAO().getById(id);
    }

    public void setDaoFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
