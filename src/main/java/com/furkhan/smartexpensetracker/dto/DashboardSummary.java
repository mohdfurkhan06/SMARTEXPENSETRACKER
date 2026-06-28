package com.furkhan.smartexpensetracker.dto;

public class DashboardSummary {

    private Double totalExpenses;
    private Double thisMonthExpenses;
    private Long totalTransactions;

    public DashboardSummary() {
    }

    public DashboardSummary(Double totalExpenses, Double thisMonthExpenses, Long totalTransactions) {
        this.totalExpenses = totalExpenses;
        this.thisMonthExpenses = thisMonthExpenses;
        this.totalTransactions = totalTransactions;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public Double getThisMonthExpenses() {
        return thisMonthExpenses;
    }

    public void setThisMonthExpenses(Double thisMonthExpenses) {
        this.thisMonthExpenses = thisMonthExpenses;
    }

    public Long getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(Long totalTransactions) {
        this.totalTransactions = totalTransactions;
    }
}