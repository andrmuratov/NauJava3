package ru.andrmuratov.NauJava.service;

import ru.andrmuratov.NauJava.entity.Report;

public interface ReportService {
    Report getReportById(Long id);
    Long createReport();
    void generateReportAsync(Long reportId);
}