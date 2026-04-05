package ru.andrmuratov.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.andrmuratov.NauJava.entity.Report;
import ru.andrmuratov.NauJava.entity.ReportStatus;
import ru.andrmuratov.NauJava.service.ReportService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> createReport() {
        Long id = reportService.createReport();
        reportService.generateReportAsync(id);
        Map<String, Long> response = new HashMap<>();
        response.put("reportId", id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReport(@PathVariable Long id) {
        Report report = reportService.getReportById(id);
        if (report == null) {
            return ResponseEntity.notFound().build();
        }
        if (report.getStatus() == ReportStatus.CREATED) {
            return ResponseEntity.ok("Report is being generated");
        }
        if (report.getStatus() == ReportStatus.ERROR) {
            return ResponseEntity.status(500).body(report.getContent());
        }
        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.TEXT_HTML).body(report.getContent());
    }
}