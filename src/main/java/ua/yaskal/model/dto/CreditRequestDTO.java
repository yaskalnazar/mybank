package ua.yaskal.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreditRequestDTO {
    private long applicantId;
    private BigDecimal creditRate;
    private BigDecimal creditLimit;
    private LocalDate creationDate;

    public CreditRequestDTO(long applicantId, BigDecimal creditRate, BigDecimal creditLimit, LocalDate creationDate) {
        this.applicantId = applicantId;
        this.creditRate = creditRate;
        this.creditLimit = creditLimit;
        this.creationDate = creationDate;
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
}
