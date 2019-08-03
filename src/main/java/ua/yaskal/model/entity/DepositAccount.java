package ua.yaskal.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DepositAccount extends Account {
    private BigDecimal depositAmount;
    private BigDecimal depositRate;
    private LocalDate depositEndDate;


    @Override
    public AccountType getAccountType() {
        return AccountType.DEPOSIT;
    }

    public DepositAccount(long id, BigDecimal balance, LocalDate closingDate, User owner, AccountStatus accountStatus,
                          BigDecimal depositAmount, BigDecimal depositRate, LocalDate depositEndDate) {
        super(id, balance, closingDate, owner, accountStatus);
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

    public static class DepositAccountBuilder {
        private long id;
        private BigDecimal balance;
        private LocalDate closingDate;
        private User owner;
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

        public DepositAccountBuilder setOwner(User owner) {
            this.owner = owner;
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
            return new DepositAccount(id, balance, closingDate, owner, accountStatus, depositAmount, depositRate, depositEndDate);
        }
    }
}
