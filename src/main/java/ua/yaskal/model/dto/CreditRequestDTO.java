package ua.yaskal.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * This DTO used for USER creating new credit request.
 *
 * @author Nazar Yaskal
 * @see ua.yaskal.controller.command.user.NewCreditRequestCommand
 */
public class CreditRequestDTO {
    private long applicantId;
    private BigDecimal creditRate;
    private BigDecimal creditLimit;
    private LocalDateTime creationDate;

    public CreditRequestDTO(long applicantId, BigDecimal creditRate, BigDecimal creditLimit, LocalDateTime creationDate) {
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
