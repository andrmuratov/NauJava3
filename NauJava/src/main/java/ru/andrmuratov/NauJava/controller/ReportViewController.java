package ru.andrmuratov.NauJava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
public class ReportViewController {

    @GetMapping
    public String reportPage() {
        return "report";
    }
}