package ua.yaskal.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment {
    private long id;
    private Account requesterAccount;
    private Account payerAccount;
    private BigDecimal amount;
    private LocalDate date;
    private PaymentStatus paymentStatus;

    public enum PaymentStatus {
        PENDING,
        PAID,
        REJECTED;
    }

    public Payment(long id, Account requesterAccount, Account payerAccount, BigDecimal amount, LocalDate date, PaymentStatus paymentStatus) {
        this.id = id;
        this.requesterAccount = requesterAccount;
        this.payerAccount = payerAccount;
        this.amount = amount;
        this.date = date;
        this.paymentStatus = paymentStatus;
    }

    public static class PaymentBuilder {
        private long id;
        private Account requesterAccount;
        private Account payerAccount;
        private BigDecimal amount;
        private LocalDate date;
        private PaymentStatus paymentStatus;

        public PaymentBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public PaymentBuilder setRequesterAccount(Account requesterAccount) {
            this.requesterAccount = requesterAccount;
            return this;
        }

        public PaymentBuilder setPayerAccount(Account payerAccount) {
            this.payerAccount = payerAccount;
            return this;
        }

        public PaymentBuilder setAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public PaymentBuilder setDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public PaymentBuilder setPaymentStatus(PaymentStatus paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }

        public Payment build() {
            return new Payment(id, requesterAccount, payerAccount, amount, date, paymentStatus);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getRequesterAccount() {
        return requesterAccount;
    }

    public void setRequesterAccount(Account requesterAccount) {
        this.requesterAccount = requesterAccount;
    }

    public Account getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(Account payerAccount) {
        this.payerAccount = payerAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
