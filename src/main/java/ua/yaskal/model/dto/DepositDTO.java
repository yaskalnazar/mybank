package ua.yaskal.model.dto;

import java.math.BigDecimal;

public class DepositDTO {
    private BigDecimal depositAmount;
    private BigDecimal depositRate;
    private int monthsAmount;
    private long ownerId;

    public DepositDTO(BigDecimal depositAmount, BigDecimal depositRate, int monthsAmount, long ownerId) {
        this.depositAmount = depositAmount;
        this.depositRate = depositRate;
        this.monthsAmount = monthsAmount;
        this.ownerId = ownerId;
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

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }
}
