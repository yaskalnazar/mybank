package ua.yaskal.model.service;

import ua.yaskal.model.dao.DaoFactory;
import ua.yaskal.model.dao.DepositDAO;
import ua.yaskal.model.dto.DepositDTO;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.DepositAccount;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DepositService {



    public DepositAccount openNewDeposit(DepositDTO depositDTO){
        DepositDAO depositDAO = DaoFactory.getInstance().createDepositDAO();
        DepositAccount depositAccount = DepositAccount.getBuilder()
                .setBalance(new BigDecimal(0))
                .setClosingDate(LocalDate.now().plusYears(5))
                .setOwnerId(depositDTO.getOwnerId())
                .setAccountStatus(Account.AccountStatus.ACTIVE)
                .setDepositAmount(depositDTO.getDepositAmount())
                .setDepositRate(depositDTO.getDepositRate())
                .setDepositEndDate(LocalDate.now().plusMonths(depositDTO.getMonthsAmount()))
                .build();

        depositAccount.setId(depositDAO.addNew(depositAccount));

        return depositAccount;
    }

    public List<DepositAccount> getAll(){
        return DaoFactory.getInstance().createDepositDAO().getAll();
    }

    public List<DepositAccount> getAllByOwnerId(long ownerId){
        return DaoFactory.getInstance().createDepositDAO().getAllByOwnerId(ownerId);
    }

    public List<DepositAccount> getAllByOwnerIdAndStatus(long ownerId, Account.AccountStatus status){
        return DaoFactory.getInstance().createDepositDAO().getAllByOwnerIdAndStatus(ownerId, status);
    }
}
