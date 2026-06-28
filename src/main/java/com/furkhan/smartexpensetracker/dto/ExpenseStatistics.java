package com.furkhan.smartexpensetracker.dto;

public class ExpenseStatistics {

    private Double highestExpense;
    private Double lowestExpense;
    private Double averageExpense;

    public ExpenseStatistics() {
    }

    public ExpenseStatistics(Double highestExpense,
                             Double lowestExpense,
                             Double averageExpense) {
        this.highestExpense = highestExpense;
        this.lowestExpense = lowestExpense;
        this.averageExpense = averageExpense;
    }

    public Double getHighestExpense() {
        return highestExpense;
    }

    public void setHighestExpense(Double highestExpense) {
        this.highestExpense = highestExpense;
    }

    public Double getLowestExpense() {
        return lowestExpense;
    }

    public void setLowestExpense(Double lowestExpense) {
        this.lowestExpense = lowestExpense;
    }

    public Double getAverageExpense() {
        return averageExpense;
    }

    public void setAverageExpense(Double averageExpense) {
        this.averageExpense = averageExpense;
    }
}