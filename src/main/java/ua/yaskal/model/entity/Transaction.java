package ua.yaskal.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private long id;
    private long senderAccountId;
    private long receiverAccountId;
    private BigDecimal transactionAmount;
    private LocalDate date;

    public Transaction(long id, long senderAccountId, long receiverAccountId, BigDecimal transactionAmount, LocalDate date) {
        this.id = id;
        this.senderAccountId = senderAccountId;
        this.receiverAccountId = receiverAccountId;
        this.transactionAmount = transactionAmount;
        this.date = date;
    }

    public static TransactionBuilder getBuilder(){
        return new TransactionBuilder();
    }

    public static class TransactionBuilder{
        private long id;
        private long senderAccountId;
        private long receiverAccountId;
        private BigDecimal transactionAmount;
        private LocalDate date;

        public TransactionBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public TransactionBuilder setSenderAccount(long senderAccountId) {
            this.senderAccountId = senderAccountId;
            return this;
        }

        public TransactionBuilder setReceiverAccount(long receiverAccountId) {
            this.receiverAccountId = receiverAccountId;
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
            return new Transaction(id, senderAccountId, receiverAccountId, transactionAmount, date);
        }

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(long senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public long getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(long receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
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
