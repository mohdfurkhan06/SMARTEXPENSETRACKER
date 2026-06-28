package com.furkhan.smartexpensetracker.controller;

import com.furkhan.smartexpensetracker.dto.CategorySummary;
import com.furkhan.smartexpensetracker.dto.DashboardSummary;
import com.furkhan.smartexpensetracker.dto.ExpenseStatistics;
import com.furkhan.smartexpensetracker.dto.MonthlySummary;
import com.furkhan.smartexpensetracker.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final ExpenseService expenseService;

    public DashboardController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/summary")
    public DashboardSummary getDashboardSummary() {
        return expenseService.getDashboardSummary();
    }

    @GetMapping("/category-summary")
    public List<CategorySummary> getCategorySummary() {
        return expenseService.getCategorySummary();
    }

    @GetMapping("/monthly-summary")
    public List<MonthlySummary> getMonthlySummary() {
        return expenseService.getMonthlySummary();
    }
    @GetMapping("/statistics")
public ExpenseStatistics getExpenseStatistics() {
    return expenseService.getExpenseStatistics();
}
}