package com.furkhan.smartexpensetracker.service;

import com.furkhan.smartexpensetracker.dto.CategorySummary;
import com.furkhan.smartexpensetracker.dto.DashboardSummary;
import com.furkhan.smartexpensetracker.dto.ExpenseRequest;
import com.furkhan.smartexpensetracker.dto.ExpenseStatistics;
import com.furkhan.smartexpensetracker.dto.MonthlySummary;
import com.furkhan.smartexpensetracker.entity.Expense;
import com.furkhan.smartexpensetracker.entity.User;
import com.furkhan.smartexpensetracker.exception.ResourceNotFoundException;
import com.furkhan.smartexpensetracker.repository.ExpenseRepository;
import com.furkhan.smartexpensetracker.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository,
                          UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    public String addExpense(ExpenseRequest request) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Expense expense = new Expense();

        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setDescription(request.getDescription());
        expense.setUser(user);

        expenseRepository.save(expense);

        return "Expense added successfully";
    }

    public List<Expense> getMyExpenses() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return expenseRepository.findByUser(user);
    }

    public String updateExpense(Long id, ExpenseRequest request) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Expense expense = expenseRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setDescription(request.getDescription());

        expenseRepository.save(expense);

        return "Expense updated successfully";
    }

    public DashboardSummary getDashboardSummary() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate today = LocalDate.now();

        Double totalExpenses = expenseRepository.getTotalExpenses(user);
        Double thisMonthExpenses = expenseRepository.getThisMonthExpenses(
                user,
                today.withDayOfMonth(1),
                today.withDayOfMonth(today.lengthOfMonth()));

        Long totalTransactions = expenseRepository.countByUser(user);

        return new DashboardSummary(
                totalExpenses,
                thisMonthExpenses,
                totalTransactions);
    }

    public List<CategorySummary> getCategorySummary() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return expenseRepository.getCategorySummary(user);
    }

    public List<MonthlySummary> getMonthlySummary() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Expense> expenses =
                expenseRepository.findAllByUserOrderByExpenseDateAsc(user);

        Map<String, Double> monthlyTotals = new LinkedHashMap<>();

        for (Expense expense : expenses) {

            String month = expense.getExpenseDate().getYear() + "-"
                    + String.format("%02d",
                    expense.getExpenseDate().getMonthValue());

            monthlyTotals.put(
                    month,
                    monthlyTotals.getOrDefault(month, 0.0)
                            + expense.getAmount());
        }

        List<MonthlySummary> result = new ArrayList<>();

        for (Map.Entry<String, Double> entry : monthlyTotals.entrySet()) {
            result.add(new MonthlySummary(
                    entry.getKey(),
                    entry.getValue()));
        }

        return result;
    }

    public List<Expense> searchExpenses(String keyword) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return expenseRepository.findByUserAndTitleContainingIgnoreCase(user, keyword);
    }

    public List<Expense> filterExpenses(LocalDate startDate,
                                        LocalDate endDate) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return expenseRepository.findByUserAndExpenseDateBetween(
                user,
                startDate,
                endDate);
    }

    public Page<Expense> getExpensesPaginated(int page, int size) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Pageable pageable = PageRequest.of(page, size);

        return expenseRepository.findByUser(user, pageable);
    }

    public List<Expense> getExpensesSorted(String sortBy) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return expenseRepository.findByUser(
                user,
                Sort.by(sortBy).ascending());
    }

    public List<Expense> filterByCategory(String category) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return expenseRepository.findByUserAndCategoryIgnoreCase(user, category);
    }

    public List<Expense> filterByAmount(Double minAmount,
                                        Double maxAmount) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return expenseRepository.findByUserAndAmountBetween(
                user,
                minAmount,
                maxAmount);
    }

    public ExpenseStatistics getExpenseStatistics() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new ExpenseStatistics(
                expenseRepository.getHighestExpense(user),
                expenseRepository.getLowestExpense(user),
                expenseRepository.getAverageExpense(user));
    }
}