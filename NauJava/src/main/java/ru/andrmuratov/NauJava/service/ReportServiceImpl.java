package ru.andrmuratov.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.andrmuratov.NauJava.entity.Report;
import ru.andrmuratov.NauJava.entity.ReportStatus;
import ru.andrmuratov.NauJava.entity.User;
import ru.andrmuratov.NauJava.repository.ReportRepository;
import ru.andrmuratov.NauJava.repository.UserRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.StreamSupport;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Report getReportById(Long id) {
        return reportRepository.findById(id).orElse(null);
    }

    @Override
    public Long createReport() {
        Report report = new Report();
        report.setStatus(ReportStatus.CREATED);
        reportRepository.save(report);
        return report.getId();
    }

    @Override
    public void generateReportAsync(Long reportId) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                long startTime = System.currentTimeMillis();

                long[] userCount = new long[1];
                Thread countUsersThread = new Thread(() -> {
                    userCount[0] = userRepository.count();
                });

                List<User>[] usersList = new List[1];
                Thread getListThread = new Thread(() -> {
                    usersList[0] = StreamSupport.stream(userRepository.findAll().spliterator(), false).toList();
                });

                countUsersThread.start();
                getListThread.start();

                countUsersThread.join();
                getListThread.join();

                long elapsed = System.currentTimeMillis() - startTime;

                Report report = reportRepository.findById(reportId).orElseThrow();
                StringBuilder html = new StringBuilder();
                html.append("<!DOCTYPE html><html><head><title>Report</title></head><body>");
                html.append("<h1>Report</h1>");
                html.append("<table border='1'><tr><th>Metric</th><th>Value</th><th>Time (ms)</th></tr>");
                html.append("<tr><td>Users count</td><td>").append(userCount[0]).append("</td><td>").append(elapsed).append("</td></tr>");
                html.append("<tr><td>Users list</td><td>").append(usersList[0].size()).append("</td><td>").append(elapsed).append("</td></tr>");
                html.append("<tr><td>Total time</td><td colspan='2'>").append(elapsed).append(" ms</td></tr>");
                html.append("</table></body></html>");

                report.setContent(html.toString());
                report.setStatus(ReportStatus.COMPLETED);
                reportRepository.save(report);

            } catch (Exception e) {
                Report report = reportRepository.findById(reportId).orElseThrow();
                report.setStatus(ReportStatus.ERROR);
                report.setContent("Error: " + e.getMessage());
                reportRepository.save(report);
            }
        });
    }
}