package com.furkhan.smartexpensetracker.service;

import com.furkhan.smartexpensetracker.entity.Expense;
import com.furkhan.smartexpensetracker.entity.User;
import com.furkhan.smartexpensetracker.exception.ResourceNotFoundException;
import com.furkhan.smartexpensetracker.repository.ExpenseRepository;
import com.furkhan.smartexpensetracker.repository.UserRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfExportService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public PdfExportService(ExpenseRepository expenseRepository,
                            UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    public byte[] exportExpenses() throws Exception {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Expense> expenses = expenseRepository.findByUser(user);

        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, outputStream);

        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Expense Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(5);

        table.addCell("Title");
        table.addCell("Amount");
        table.addCell("Category");
        table.addCell("Date");
        table.addCell("Description");

        for (Expense expense : expenses) {
            table.addCell(expense.getTitle());
            table.addCell(String.valueOf(expense.getAmount()));
            table.addCell(expense.getCategory());
            table.addCell(expense.getExpenseDate().toString());
            table.addCell(
                    expense.getDescription() == null
                            ? ""
                            : expense.getDescription()
            );
        }

        document.add(table);

        document.close();

        return outputStream.toByteArray();
    }
}