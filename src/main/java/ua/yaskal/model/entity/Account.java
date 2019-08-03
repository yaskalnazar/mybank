package ua.yaskal.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class Account {
    private long id;
    private BigDecimal balance;
    private LocalDate closingDate;
    private User owner;
    private AccountStatus accountStatus;

    public abstract AccountType getAccountType();


    public enum AccountStatus {
        ACTIVE,
        CLOSED,
        BLOCKED;
    }

    public enum AccountType {
        CREDIT,
        DEPOSIT;
    }

    public Account(long id, BigDecimal balance, LocalDate closingDate, User owner, AccountStatus accountStatus) {
        this.id = id;
        this.balance = balance;
        this.closingDate = closingDate;
        this.owner = owner;
        this.accountStatus = accountStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDate getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }
}
