package ua.yaskal.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * This realization of abstract class {@link Account} that implements Deposit Policy.
 * In such entity some part of the money is blocked for a certain period after which it is returned to the balance
 * with interest, after which you can make a new contribution.
 * The class has POJO structure.
 *
 * @author Nazar Yaskal
 * @see ua.yaskal.model.entity.Account
 */
public class DepositAccount extends Account {
    private BigDecimal depositAmount;
    private BigDecimal depositRate;
    private LocalDate depositEndDate;


    @Override
    public AccountType getAccountType() {
        return AccountType.DEPOSIT;
    }

    public DepositAccount(long id, BigDecimal balance, LocalDate closingDate, long ownerId, AccountStatus accountStatus,
                          BigDecimal depositAmount, BigDecimal depositRate, LocalDate depositEndDate) {
        super(id, balance, closingDate, ownerId, accountStatus);
        this.depositAmount = depositAmount;
        this.depositRate = depositRate;
        this.depositEndDate = depositEndDate;
    }

    public static DepositAccountBuilder getBuilder(){
        return new DepositAccountBuilder();
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public BigDecimal getDepositRate() {
        return depositRate;
    }

    public void setDepositRate(BigDecimal depositRate) {
        this.depositRate = depositRate;
    }

    public LocalDate getDepositEndDate() {
        return depositEndDate;
    }

    public void setDepositEndDate(LocalDate depositEndDate) {
        this.depositEndDate = depositEndDate;
    }

    /**
     * This class realize pattern Builder for class {@link DepositAccount}
     *
     * @author Nazar Yaskal
     * @see DepositAccount
     */
    public static class DepositAccountBuilder {
        private long id;
        private BigDecimal balance;
        private LocalDate closingDate;
        private long ownerId;
        private AccountStatus accountStatus;
        private BigDecimal depositAmount;
        private BigDecimal depositRate;
        private LocalDate depositEndDate;

        public DepositAccountBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public DepositAccountBuilder setBalance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public DepositAccountBuilder setClosingDate(LocalDate closingDate) {
            this.closingDate = closingDate;
            return this;
        }

        public DepositAccountBuilder setOwnerId(long ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public DepositAccountBuilder setAccountStatus(AccountStatus accountStatus) {
            this.accountStatus = accountStatus;
            return this;
        }

        public DepositAccountBuilder setDepositAmount(BigDecimal depositAmount) {
            this.depositAmount = depositAmount;
            return this;
        }

        public DepositAccountBuilder setDepositRate(BigDecimal depositRate) {
            this.depositRate = depositRate;
            return this;
        }

        public DepositAccountBuilder setDepositEndDate(LocalDate depositEndDate) {
            this.depositEndDate = depositEndDate;
            return this;
        }

        public DepositAccount build() {
            return new DepositAccount(id, balance, closingDate, ownerId, accountStatus, depositAmount, depositRate, depositEndDate);
        }
    }
}
