package com.furkhan.smartexpensetracker.controller;
import com.furkhan.smartexpensetracker.dto.ExpenseResponse;
import com.furkhan.smartexpensetracker.dto.CategorySummary;
import com.furkhan.smartexpensetracker.dto.DashboardSummary;
import com.furkhan.smartexpensetracker.dto.ExpenseRequest;
import com.furkhan.smartexpensetracker.dto.ExpenseStatistics;
import com.furkhan.smartexpensetracker.dto.MonthlySummary;
import com.furkhan.smartexpensetracker.entity.Expense;
import com.furkhan.smartexpensetracker.response.ApiResponse;
import com.furkhan.smartexpensetracker.service.ExpenseService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ApiResponse<String> addExpense(@Valid @RequestBody ExpenseRequest request) {

        return new ApiResponse<>(
                true,
                "Expense added successfully",
                expenseService.addExpense(request)
        );
    }

    @GetMapping
    public ApiResponse<List<ExpenseResponse>> getMyExpenses() {

        return new ApiResponse<>(
                true,
                "Success",
                expenseService.getMyExpenses()
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<String> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequest request) {

        return new ApiResponse<>(
                true,
                "Expense updated successfully",
                expenseService.updateExpense(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteExpense(@PathVariable Long id) {

        expenseService.deleteExpense(id);

        return new ApiResponse<>(
                true,
                "Expense deleted successfully",
                null
        );
    }

    @GetMapping("/search")
    public ApiResponse<List<ExpenseResponse>> searchExpenses(
            @RequestParam String keyword) {

        return new ApiResponse<>(
                true,
                "Success",
                expenseService.searchExpenses(keyword)
        );
    }

    @GetMapping("/filter")
    public ApiResponse<List<ExpenseResponse>> filterExpenses(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        return new ApiResponse<>(
                true,
                "Success",
                expenseService.filterExpenses(startDate, endDate)
        );
    }

    @GetMapping("/page")
    public ApiResponse<Page<Expense>> getExpensesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return new ApiResponse<>(
                true,
                "Success",
                expenseService.getExpensesPaginated(page, size)
        );
    }

    @GetMapping("/sort")
    public ApiResponse<List<ExpenseResponse>> getExpensesSorted(
            @RequestParam(defaultValue = "expenseDate") String sortBy) {

        return new ApiResponse<>(
                true,
                "Success",
                expenseService.getExpensesSorted(sortBy)
        );
    }

    @GetMapping("/category")
    public ApiResponse<List<ExpenseResponse>> filterByCategory(
            @RequestParam String category) {

        return new ApiResponse<>(
                true,
                "Success",
                expenseService.filterByCategory(category)
        );
    }

    @GetMapping("/amount")
    public ApiResponse<List<ExpenseResponse>> filterByAmount(
            @RequestParam Double minAmount,
            @RequestParam Double maxAmount) {

        return new ApiResponse<>(
                true,
                "Success",
                expenseService.filterByAmount(minAmount, maxAmount)
        );
    }

    @GetMapping("/statistics")
    public ApiResponse<ExpenseStatistics> getExpenseStatistics() {

        return new ApiResponse<>(
                true,
                "Success",
                expenseService.getExpenseStatistics()
        );
    }

    @GetMapping("/dashboard")
    public ApiResponse<DashboardSummary> getDashboardSummary() {

        return new ApiResponse<>(
                true,
                "Success",
                expenseService.getDashboardSummary()
        );
    }

    @GetMapping("/category-summary")
    public ApiResponse<List<CategorySummary>> getCategorySummary() {

        return new ApiResponse<>(
                true,
                "Success",
                expenseService.getCategorySummary()
        );
    }

    @GetMapping("/monthly-summary")
    public ApiResponse<List<MonthlySummary>> getMonthlySummary() {

        return new ApiResponse<>(
                true,
                "Success",
                expenseService.getMonthlySummary()
        );
    }
}