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

    public CreditAccount(long id, BigDecimal balance, LocalDate closingDate, User owner, AccountStatus accountStatus,
                         BigDecimal creditRate, BigDecimal creditLimit, BigDecimal accruedInterest) {
        super(id, balance, closingDate, owner, accountStatus);
        this.creditRate = creditRate;
        this.creditLimit = creditLimit;
        this.accruedInterest = accruedInterest;
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

    public static class CreditAccountBuilder {
        private long id;
        private BigDecimal balance;
        private LocalDate closingDate;
        private User owner;
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

        public CreditAccountBuilder setOwner(User owner) {
            this.owner = owner;
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
            return new CreditAccount(id, balance, closingDate, owner, accountStatus, creditRate, creditLimit, accruedInterest);
        }
    }
}
