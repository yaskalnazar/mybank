package ua.yaskal.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreditRequest {
    private long id;
    private User applicant;
    private BigDecimal creditRate;
    private BigDecimal creditLimit;
    private LocalDate creationDate;
    private CreditRequestStatus creditRequestStatus;

    public enum CreditRequestStatus {
        PENDING,
        APPROVED,
        REJECTED;
    }

    public CreditRequest(long id, User applicant, BigDecimal creditRate, BigDecimal creditLimit, LocalDate creationDate, CreditRequestStatus creditRequestStatus) {
        this.id = id;
        this.applicant = applicant;
        this.creditRate = creditRate;
        this.creditLimit = creditLimit;
        this.creationDate = creationDate;
        this.creditRequestStatus = creditRequestStatus;
    }
}
