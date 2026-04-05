package ru.andrmuratov.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.andrmuratov.NauJava.entity.Report;

public interface ReportRepository extends CrudRepository<Report, Long> {
}