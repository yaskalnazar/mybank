package ua.yaskal.model.dto;

import ua.yaskal.model.entity.User;

import java.math.BigDecimal;

public class DepositDTO {
    private BigDecimal depositAmount;
    private BigDecimal depositRate;
    private int monthsAmount;
    private User owner;

    public DepositDTO(BigDecimal depositAmount, BigDecimal depositRate, int monthsAmount, User owner) {
        this.depositAmount = depositAmount;
        this.depositRate = depositRate;
        this.monthsAmount = monthsAmount;
        this.owner = owner;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
