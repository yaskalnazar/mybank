package ua.yaskal.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreditAccount extends Account {
    private BigDecimal creditRate;
    private BigDecimal creditLimit;
    private BigDecimal accruedInterest;


    @Override
    public AccountType getAccountType() {
        return AccountType.CREDIT;
    }

    public CreditAccount(long id, BigDecimal balance, LocalDate closingDate, long ownerId, AccountStatus accountStatus,
                         BigDecimal creditRate, BigDecimal creditLimit, BigDecimal accruedInterest) {
        super(id, balance, closingDate, ownerId, accountStatus);
        this.creditRate = creditRate;
        this.creditLimit = creditLimit;
        this.accruedInterest = accruedInterest;
    }

    public static CreditAccountBuilder getBuilder(){
        return new CreditAccountBuilder();
    }

    public BigDecimal getCreditRate() {
        return creditRate;
    }

    public void setCreditRate(BigDecimal creditRate) {
        this.creditRate = creditRate;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getAccruedInterest() {
        return accruedInterest;
    }

    public void setAccruedInterest(BigDecimal accruedInterest) {
        this.accruedInterest = accruedInterest;
    }

    public AccountStatus getAccountStatus() {
        return super.getAccountStatus();
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        super.setAccountStatus(accountStatus);
    }

    public static class CreditAccountBuilder {
        private long id;
        private BigDecimal balance;
        private LocalDate closingDate;
        private long ownerId;
        private AccountStatus accountStatus;
        private BigDecimal creditRate;
        private BigDecimal creditLimit;
        private BigDecimal accruedInterest;

        public CreditAccountBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public CreditAccountBuilder setBalance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public CreditAccountBuilder setClosingDate(LocalDate closingDate) {
            this.closingDate = closingDate;
            return this;
        }

        public CreditAccountBuilder setOwnerId(long ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public CreditAccountBuilder setAccountStatus(AccountStatus accountStatus) {
            this.accountStatus = accountStatus;
            return this;
        }

        public CreditAccountBuilder setCreditRate(BigDecimal creditRate) {
            this.creditRate = creditRate;
            return this;
        }

        public CreditAccountBuilder setCreditLimit(BigDecimal creditLimit) {
            this.creditLimit = creditLimit;
            return this;
        }

        public CreditAccountBuilder setAccruedInterest(BigDecimal accruedInterest) {
            this.accruedInterest = accruedInterest;
            return this;
        }

        public CreditAccount build() {
            return new CreditAccount(id, balance, closingDate, ownerId, accountStatus, creditRate, creditLimit, accruedInterest);
        }
    }
}
