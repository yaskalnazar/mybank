package ua.yaskal.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * This entity represents transactions that made by users and system.
 * USER or system can send unlimited number of transaction. All all fields are required.
 * The class has POJO structure.
 *
 * @author Nazar Yaskal
 */
public class Transaction {
    private long id;
    private long senderAccountId;
    private long receiverAccountId;
    private BigDecimal transactionAmount;
    private LocalDateTime date;

    public Transaction(long id, long senderAccountId, long receiverAccountId, BigDecimal transactionAmount, LocalDateTime date) {
        this.id = id;
        this.senderAccountId = senderAccountId;
        this.receiverAccountId = receiverAccountId;
        this.transactionAmount = transactionAmount;
        this.date = date;
    }

    public static TransactionBuilder getBuilder() {
        return new TransactionBuilder();
    }

    /**
     * This class realize pattern Builder for class {@link Transaction}
     *
     * @author Nazar Yaskal
     * @see Transaction
     */
    public static class TransactionBuilder {
        private long id;
        private long senderAccountId;
        private long receiverAccountId;
        private BigDecimal transactionAmount;
        private LocalDateTime date;

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

        public TransactionBuilder setDate(LocalDateTime date) {
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
