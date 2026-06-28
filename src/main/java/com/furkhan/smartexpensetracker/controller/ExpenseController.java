package com.furkhan.smartexpensetracker.controller;

import org.springframework.web.bind.annotation.*;
import com.furkhan.smartexpensetracker.dto.ExpenseStatistics;
import com.furkhan.smartexpensetracker.dto.ExpenseRequest;
import com.furkhan.smartexpensetracker.entity.Expense;
import com.furkhan.smartexpensetracker.response.ApiResponse;

import java.time.LocalDate;
import java.util.List;
import com.furkhan.smartexpensetracker.service.ExpenseService;
import org.springframework.data.domain.Page;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public String addExpense(@Valid @RequestBody ExpenseRequest request) {
        return expenseService.addExpense(request);
    }
    @GetMapping
    public List<Expense> getMyExpenses() {
        return expenseService.getMyExpenses();
    }
    @PutMapping("/{id}")
    public String updateExpense(@PathVariable Long id,
                               @Valid @RequestBody ExpenseRequest request) {
            
    System.out.println("Controller reached");
        return expenseService.updateExpense(id, request);
    }
   @GetMapping("/search")
    public List<Expense> searchExpenses(@RequestParam String keyword) {
    return expenseService.searchExpenses(keyword);
}
    @GetMapping("/filter")
    public List<Expense> filterExpenses(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        return expenseService.filterExpenses(startDate, endDate);
    }
    @GetMapping("/page")
public Page<Expense> getExpensesPaginated(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {

    return expenseService.getExpensesPaginated(page, size);
}
@GetMapping("/sort")
public List<Expense> getExpensesSorted(
        @RequestParam(defaultValue = "expenseDate") String sortBy) {

    return expenseService.getExpensesSorted(sortBy);
}
@GetMapping("/category")
public List<Expense> filterByCategory(
        @RequestParam String category) {

    return expenseService.filterByCategory(category);
}
@GetMapping("/amount")
public List<Expense> filterByAmount(
        @RequestParam Double minAmount,
        @RequestParam Double maxAmount) {

    return expenseService.filterByAmount(minAmount, maxAmount);
}
@GetMapping("/statistics")
public ExpenseStatistics getExpenseStatistics() {
    return expenseService.getExpenseStatistics();
}
}