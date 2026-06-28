package com.furkhan.smartexpensetracker.dto;

public class MonthlySummary {

    private String month;
    private Double totalAmount;

    public MonthlySummary() {
    }

    public MonthlySummary(String month, Double totalAmount) {
        this.month = month;
        this.totalAmount = totalAmount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}