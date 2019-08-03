package ua.yaskal.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private long id;
    private Account senderAccount;
    private Account receiverAccount;
    private BigDecimal transactionAmount;
    private LocalDate date;

    public Transaction(long id, Account senderAccount, Account receiverAccount, BigDecimal transactionAmount, LocalDate date) {
        this.id = id;
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.transactionAmount = transactionAmount;
        this.date = date;
    }

    public static TransactionBuilder getBuilder(){
        return new TransactionBuilder();
    }

    public static class TransactionBuilder{
        private long id;
        private Account senderAccount;
        private Account receiverAccount;
        private BigDecimal transactionAmount;
        private LocalDate date;

        public TransactionBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public TransactionBuilder setSenderAccount(Account senderAccount) {
            this.senderAccount = senderAccount;
            return this;
        }

        public TransactionBuilder setReceiverAccount(Account receiverAccount) {
            this.receiverAccount = receiverAccount;
            return this;
        }

        public TransactionBuilder setTransactionAmount(BigDecimal transactionAmount) {
            this.transactionAmount = transactionAmount;
            return this;
        }

        public TransactionBuilder setDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public Transaction build() {
            return new Transaction(id, senderAccount, receiverAccount, transactionAmount, date);
        }

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(Account senderAccount) {
        this.senderAccount = senderAccount;
    }

    public Account getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(Account receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
