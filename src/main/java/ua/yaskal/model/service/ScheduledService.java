package ua.yaskal.model.service;

import org.apache.log4j.Logger;
import ua.yaskal.model.dao.DAOFactory;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.CreditAccount;
import ua.yaskal.model.entity.DepositAccount;
import ua.yaskal.model.entity.Transaction;
import ua.yaskal.model.exceptions.WrongAccountTypeException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This service makes scheduled tasks in system.
 *
 * @author Nazar Yaskal
 */
public class ScheduledService {
    private final static Logger logger = Logger.getLogger(ScheduledService.class);
    private static final long NUMBER_YEARS_OF_CARD_VALID = 5;
    private static final long WORKAROUND_ACCOUNT_ID = 12;
    private final ScheduledExecutorService scheduledExecutorService;
    private final DAOFactory daoFactory;

    /**
     * Constructor used to create the entity and register all active accounts in the system for periodic tasks
     *
     * @param scheduledExecutorService
     * @param daoFactory
     */
    public ScheduledService(ScheduledExecutorService scheduledExecutorService, DAOFactory daoFactory) {
        this.scheduledExecutorService = scheduledExecutorService;
        this.daoFactory = daoFactory;

        scheduleAccounts(daoFactory.createAccountDAO().getAllByStatus(Account.AccountStatus.ACTIVE));
    }


    /**
     * Register accounts for periodic tasks.
     *
     * @param accounts Targeted accounts.
     */
    public void scheduleAccounts(List<Account> accounts) {
        for (Account i : accounts) {
            if (i instanceof DepositAccount)
                setDepositEnd((DepositAccount) i);
            if (i instanceof CreditAccount)
                setCreditInterestAccrual((CreditAccount) i);
        }
        accounts.forEach(this::setAccountClosing);
    }

    /**
     * Register deposit for processing depositEnd.
     *
     * @param deposit Targeted deposit.
     */
    private void setDepositEnd(DepositAccount deposit) {
        if (deposit.getDepositAmount().compareTo(BigDecimal.ZERO) == 0) {
            return;
        }

        scheduledExecutorService.schedule(() -> processDepositEnd(deposit),
                calculateDaysDelay(deposit.getDepositEndDate()), TimeUnit.DAYS);

    }

    private void processDepositEnd(DepositAccount deposit){
        Transaction transaction = Transaction.getBuilder()
                .setSenderAccount(WORKAROUND_ACCOUNT_ID)
                .setReceiverAccount(deposit.getId())
                .setTransactionAmount(deposit.getDepositAmount().add(
                        deposit.getDepositAmount().multiply(deposit.getDepositRate())))
                .setDate(LocalDateTime.now())
                .build();

        daoFactory.createTransactionDAO()
                .addNew(transaction);

        daoFactory.createDepositDAO()
                .updateDepositRate(deposit.getId(), BigDecimal.ZERO);

        daoFactory.createDepositDAO()
                .updateDepositAmount(deposit.getId(), BigDecimal.ZERO);
    }

    /**
     * Register credit for adding accrual interest every first day of month.
     *
     * @param credit Targeted credit.
     */
    private void setCreditInterestAccrual(CreditAccount credit) {
        if (credit.getCreditLimit().compareTo(BigDecimal.ZERO) == 0) {
            return;
        }
        scheduledExecutorService.schedule(() -> daoFactory.createCreditDAO()
                        .increaseAccruedInterestById(credit.getId(),
                                credit.getCreditLimit().multiply(credit.getCreditRate())),
                calculateDaysDelay(LocalDate.now().plusMonths(1).withDayOfMonth(11)),
                TimeUnit.DAYS);

    }

    /**
     * Register account for closing and automatic reissue
     *
     * @param account Targeted account.
     */
    private void setAccountClosing(Account account) {
        scheduledExecutorService.schedule(() -> reissueAccount(account),
                calculateDaysDelay(account.getClosingDate()),
                TimeUnit.DAYS);
    }

    /**
     * Calculate days from now to endDate
     *
     * @param endDate
     */
    private long calculateDaysDelay(LocalDate endDate) {
        return ChronoUnit.DAYS.between(LocalDate.now(), endDate);
    }


    /**
     * Determines the type of account and causes a restart reissue
     *
     * @param account Targeted account.
     */
    private Account reissueAccount(Account account) {
        if (account instanceof DepositAccount) {
            return reissueDeposit((DepositAccount) account);
        }
        if (account instanceof CreditAccount) {
            return reissueCredit((CreditAccount) account);
        }
        throw new WrongAccountTypeException(account.getId());
    }

    private DepositAccount reissueDeposit(DepositAccount oldDeposit) {
        logger.debug("Reissue deposit id: " + oldDeposit.getId());
        DepositAccount newDeposit = DepositAccount.getBuilder()
                .setClosingDate(LocalDate.now().plusYears(NUMBER_YEARS_OF_CARD_VALID))
                .setAccountStatus(Account.AccountStatus.ACTIVE)
                .setBalance(BigDecimal.ZERO)
                .setOwnerId(oldDeposit.getOwnerId())
                .setDepositAmount(oldDeposit.getDepositAmount())
                .setDepositRate(oldDeposit.getDepositRate())
                .setDepositEndDate(oldDeposit.getDepositEndDate())
                .build();

        newDeposit.setId(daoFactory.createDepositDAO().addNew(newDeposit));

        oldDeposit.setAccountStatus(Account.AccountStatus.CLOSED);
        oldDeposit.setDepositAmount(BigDecimal.ZERO);
        oldDeposit.setDepositRate(BigDecimal.ZERO);
        daoFactory.createDepositDAO().update(oldDeposit);

        transferBalance(oldDeposit, newDeposit);

        return newDeposit;
    }

    private CreditAccount reissueCredit(CreditAccount oldCredit) {
        logger.debug("Reissue credit id:" + oldCredit.getId());
        CreditAccount newCredit = CreditAccount.getBuilder()
                .setClosingDate(LocalDate.now().plusYears(NUMBER_YEARS_OF_CARD_VALID))
                .setAccountStatus(Account.AccountStatus.ACTIVE)
                .setBalance(BigDecimal.ZERO)
                .setOwnerId(oldCredit.getOwnerId())
                .setAccruedInterest(oldCredit.getAccruedInterest())
                .setCreditLimit(oldCredit.getCreditLimit())
                .setCreditRate(oldCredit.getCreditRate())
                .build();

        newCredit.setId(daoFactory.createCreditDAO().addNew(newCredit));


        oldCredit.setAccountStatus(Account.AccountStatus.CLOSED);
        oldCredit.setAccruedInterest(BigDecimal.ZERO);
        oldCredit.setCreditLimit(BigDecimal.ZERO);
        oldCredit.setCreditRate(BigDecimal.ZERO);
        daoFactory.createCreditDAO().update(oldCredit);

        transferBalance(oldCredit, newCredit);

        return newCredit;
    }


    private void transferBalance(Account oldAccount, Account newAccount) {
        logger.debug("Transfer balance from " + oldAccount.getId() + " to " + newAccount.getId());
        daoFactory.createTransactionDAO().addNew(Transaction.getBuilder()
                .setDate(LocalDateTime.now())
                .setTransactionAmount(oldAccount.getBalance())
                .setSenderAccount(oldAccount.getId())
                .setReceiverAccount(newAccount.getId())
                .build());
    }


}
