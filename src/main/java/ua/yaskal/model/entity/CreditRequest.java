package ua.yaskal.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreditRequest {
    private long id;
    private long applicantId;
    private BigDecimal creditRate;
    private BigDecimal creditLimit;
    private LocalDate creationDate;
    private CreditRequestStatus creditRequestStatus;

    public enum CreditRequestStatus {
        PENDING,
        APPROVED,
        REJECTED;
    }

    public CreditRequest(long id, long applicantId, BigDecimal creditRate, BigDecimal creditLimit,
                         LocalDate creationDate, CreditRequestStatus creditRequestStatus) {
        this.id = id;
        this.applicantId = applicantId;
        this.creditRate = creditRate;
        this.creditLimit = creditLimit;
        this.creationDate = creationDate;
        this.creditRequestStatus = creditRequestStatus;
    }

    public static CreditRequestBuilder getBuilder(){
        return new CreditRequestBuilder();
    }

    public static class CreditRequestBuilder{
        private long id;
        private long applicantId;
        private BigDecimal creditRate;
        private BigDecimal creditLimit;
        private LocalDate creationDate;
        private CreditRequestStatus creditRequestStatus;

        public CreditRequestBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public CreditRequestBuilder setApplicantId(long applicantId) {
            this.applicantId = applicantId;
            return this;
        }

        public CreditRequestBuilder setCreditRate(BigDecimal creditRate) {
            this.creditRate = creditRate;
            return this;
        }

        public CreditRequestBuilder setCreditLimit(BigDecimal creditLimit) {
            this.creditLimit = creditLimit;
            return this;
        }

        public CreditRequestBuilder setCreationDate(LocalDate creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public CreditRequestBuilder setCreditRequestStatus(CreditRequestStatus creditRequestStatus) {
            this.creditRequestStatus = creditRequestStatus;
            return this;
        }

        public CreditRequest build() {
            return new CreditRequest(id, applicantId, creditRate, creditLimit, creationDate, creditRequestStatus);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(long applicantId) {
        this.applicantId = applicantId;
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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public CreditRequestStatus getCreditRequestStatus() {
        return creditRequestStatus;
    }

    public void setCreditRequestStatus(CreditRequestStatus creditRequestStatus) {
        this.creditRequestStatus = creditRequestStatus;
    }
}
