package com.furkhan.smartexpensetracker.repository;

import com.furkhan.smartexpensetracker.dto.CategorySummary;
import com.furkhan.smartexpensetracker.entity.Expense;
import com.furkhan.smartexpensetracker.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // CRUD
    List<Expense> findByUser(User user);

    Page<Expense> findByUser(User user, Pageable pageable);

    List<Expense> findByUser(User user, Sort sort);

    Optional<Expense> findByIdAndUser(Long id, User user);

    Long countByUser(User user);

    // Dashboard Summary
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.user = :user")
    Double getTotalExpenses(@Param("user") User user);

    @Query("""
           SELECT COALESCE(SUM(e.amount), 0)
           FROM Expense e
           WHERE e.user = :user
           AND e.expenseDate BETWEEN :startDate AND :endDate
           """)
    Double getThisMonthExpenses(
            @Param("user") User user,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    // Category Summary
    @Query("""
           SELECT new com.furkhan.smartexpensetracker.dto.CategorySummary(
               e.category,
               SUM(e.amount)
           )
           FROM Expense e
           WHERE e.user = :user
           GROUP BY e.category
           """)
    List<CategorySummary> getCategorySummary(@Param("user") User user);

    // Monthly Summary (fetch all expenses; grouping will be done in Service)
    List<Expense> findAllByUserOrderByExpenseDateAsc(User user);

    // Search
    List<Expense> findByUserAndTitleContainingIgnoreCase(User user, String title);

    // Filter by Date
    List<Expense> findByUserAndExpenseDateBetween(
            User user,
            LocalDate startDate,
            LocalDate endDate);

    // Filter by Category
    List<Expense> findByUserAndCategoryIgnoreCase(User user, String category);

    // Filter by Amount
    List<Expense> findByUserAndAmountBetween(
            User user,
            Double minAmount,
            Double maxAmount);

    // Statistics
    @Query("SELECT COALESCE(MAX(e.amount), 0) FROM Expense e WHERE e.user = :user")
    Double getHighestExpense(@Param("user") User user);

    @Query("SELECT COALESCE(MIN(e.amount), 0) FROM Expense e WHERE e.user = :user")
    Double getLowestExpense(@Param("user") User user);

    @Query("SELECT COALESCE(AVG(e.amount), 0) FROM Expense e WHERE e.user = :user")
    Double getAverageExpense(@Param("user") User user);
}