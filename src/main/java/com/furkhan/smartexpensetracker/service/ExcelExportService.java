package com.furkhan.smartexpensetracker.service;

import com.furkhan.smartexpensetracker.entity.Expense;
import com.furkhan.smartexpensetracker.entity.User;
import com.furkhan.smartexpensetracker.exception.ResourceNotFoundException;
import com.furkhan.smartexpensetracker.repository.ExpenseRepository;
import com.furkhan.smartexpensetracker.repository.UserRepository;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExcelExportService(ExpenseRepository expenseRepository,
                              UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    public byte[] exportExpenses() throws IOException {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Expense> expenses = expenseRepository.findByUser(user);

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Expenses");

        Row header = sheet.createRow(0);

        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Title");
        header.createCell(2).setCellValue("Amount");
        header.createCell(3).setCellValue("Category");
        header.createCell(4).setCellValue("Date");
        header.createCell(5).setCellValue("Description");

        int rowNum = 1;

        for (Expense expense : expenses) {

            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(expense.getId());
            row.createCell(1).setCellValue(expense.getTitle());
            row.createCell(2).setCellValue(expense.getAmount());
            row.createCell(3).setCellValue(expense.getCategory());
            row.createCell(4).setCellValue(expense.getExpenseDate().toString());

            if (expense.getDescription() != null) {
                row.createCell(5).setCellValue(expense.getDescription());
            } else {
                row.createCell(5).setCellValue("");
            }
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }
}