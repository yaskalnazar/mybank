package ua.yaskal.model.service;

import ua.yaskal.model.dao.CreditDAO;
import ua.yaskal.model.dao.DaoFactory;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.CreditAccount;
import ua.yaskal.model.entity.CreditRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CreditService {

    public List<CreditAccount> getAll(){
        return DaoFactory.getInstance().createCreditDAO().getAll();
    }

    public List<CreditAccount> getAllByOwnerId(long ownerId){
        return DaoFactory.getInstance().createCreditDAO().getAllByOwnerId(ownerId);
    }

    public CreditAccount addNew(CreditRequest creditRequest){
        CreditDAO creditDAO = DaoFactory.getInstance().createCreditDAO();
        CreditAccount creditAccount = CreditAccount.getBuilder()
                .setBalance(new BigDecimal(0))
                .setClosingDate(LocalDate.now().plusYears(5))
                .setOwnerId(creditRequest.getApplicantId())
                .setAccountStatus(Account.AccountStatus.ACTIVE)
                .setCreditLimit(creditRequest.getCreditLimit())
                .setCreditRate(creditRequest.getCreditRate())
                .setAccruedInterest(new BigDecimal(0))
                .build();

        creditAccount.setId(creditDAO.addNew(creditAccount));
        return creditAccount;

    }

    public List<CreditAccount> getAllByOwnerIdAndStatus(long ownerId, Account.AccountStatus status){
        return DaoFactory.getInstance().createCreditDAO().getAllByOwnerIdAndStatus(ownerId, status);
    }

    public CreditAccount getById(long id){
        return DaoFactory.getInstance().createCreditDAO().getById(id);
    }
}
