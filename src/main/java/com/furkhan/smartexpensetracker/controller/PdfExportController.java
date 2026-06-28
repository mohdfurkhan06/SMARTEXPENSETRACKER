package com.furkhan.smartexpensetracker.controller;

import com.furkhan.smartexpensetracker.service.PdfExportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/export")
public class PdfExportController {

    private final PdfExportService pdfExportService;

    public PdfExportController(PdfExportService pdfExportService) {
        this.pdfExportService = pdfExportService;
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> exportPdf() throws Exception {

        byte[] pdf = pdfExportService.exportExpenses();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=expenses.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}