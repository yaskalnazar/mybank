package ua.yaskal.model.dto;

import ua.yaskal.model.entity.Transaction;

import java.math.BigDecimal;

/**
 * This DTO used for USER creating new deposit contribution on already existing deposit account.
 *
 * @param transaction contains information for transfer deposit amount to the system account
 *                    from selected user account
 * @author Nazar Yaskal
 * @see ua.yaskal.controller.command.user.MakeNewContributionCommand
 */
public class NewDepositContributionDTO {
    private BigDecimal depositAmount;
    private BigDecimal depositRate;
    private int monthsAmount;
    private long depositId;
    private Transaction transaction;

    public NewDepositContributionDTO(BigDecimal depositAmount, BigDecimal depositRate, int monthsAmount, long depositId, Transaction transaction) {
        this.depositAmount = depositAmount;
        this.depositRate = depositRate;
        this.monthsAmount = monthsAmount;
        this.depositId = depositId;
        this.transaction = transaction;
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

    public int getMonthsAmount() {
        return monthsAmount;
    }

    public void setMonthsAmount(int monthsAmount) {
        this.monthsAmount = monthsAmount;
    }

    public long getDepositId() {
        return depositId;
    }

    public void setDepositId(long depositId) {
        this.depositId = depositId;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
