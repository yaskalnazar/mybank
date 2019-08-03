package ua.yaskal.model.service;

import ua.yaskal.model.dao.DaoFactory;
import ua.yaskal.model.dao.DepositDAO;
import ua.yaskal.model.dto.DepositDTO;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.DepositAccount;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DepositService {
    private UserService userService = new UserService();



    public DepositAccount openNewDeposit(DepositDTO depositDTO){
        DepositDAO depositDAO = DaoFactory.getInstance().createDepositDAO();
        DepositAccount depositAccount = DepositAccount.getBuilder()
                .setBalance(new BigDecimal(0))
                .setClosingDate(LocalDate.now().plusYears(5))
                .setOwner(depositDTO.getOwner())
                .setAccountStatus(Account.AccountStatus.ACTIVE)
                .setDepositAmount(depositDTO.getDepositAmount())
                .setDepositRate(depositDTO.getDepositRate())
                .setDepositEndDate(LocalDate.now().plusMonths(depositDTO.getMonthsAmount()))
                .build();

        depositAccount.setId(depositDAO.addNew(depositAccount));

        return depositAccount;
    }
}
