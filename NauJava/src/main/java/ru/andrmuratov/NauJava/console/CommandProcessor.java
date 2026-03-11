package ru.andrmuratov.NauJava.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.andrmuratov.NauJava.entity.Task;
import ru.andrmuratov.NauJava.service.TaskService;

import java.util.List;
import java.util.Scanner;

@Component
public class CommandProcessor {

    private final TaskService taskService;
    private final Scanner scanner;

    @Autowired
    public CommandProcessor(TaskService taskService) {
        this.taskService = taskService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("TaskFlow started. Commands: create, list, find, update, delete, exit");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) break;
            process(input);
        }
    }

    private void process(String input) {
        String[] parts = input.split(" ");
        String cmd = parts[0];
        try {
            switch (cmd) {
                case "create" -> createTask();
                case "list" -> listTasks();
                case "find" -> findTask(parts);
                case "delete" -> deleteTask(parts);
                case "update" -> updateTask(parts);
                default -> System.out.println("Unknown command");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void createTask() {
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Description: ");
        String desc = scanner.nextLine();
        System.out.print("Priority (LOW/MEDIUM/HIGH): ");
        Task.Priority priority = Task.Priority.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Status (TODO/IN_PROGRESS/DONE): ");
        Task.Status status = Task.Status.valueOf(scanner.nextLine().toUpperCase());
        taskService.createTask(title, desc, priority, status);
        System.out.println("Task created");
    }

    private void listTasks() {
        List<Task> tasks = taskService.findAll();
        tasks.forEach(System.out::println);
    }

    private void findTask(String[] parts) {
        Long id = Long.valueOf(parts[1]);
        Task task = taskService.findById(id);
        System.out.println(task != null ? task : "Not found");
    }

    private void deleteTask(String[] parts) {
        Long id = Long.valueOf(parts[1]);
        taskService.deleteById(id);
        System.out.println("Deleted");
    }

    private void updateTask(String[] parts) {
        Long id = Long.valueOf(parts[1]);
        System.out.print("New Title: ");
        String title = scanner.nextLine();
        System.out.print("New Description: ");
        String desc = scanner.nextLine();
        System.out.print("New Priority (LOW/MEDIUM/HIGH): ");
        Task.Priority priority = Task.Priority.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("New Status (TODO/IN_PROGRESS/DONE): ");
        Task.Status status = Task.Status.valueOf(scanner.nextLine().toUpperCase());
        taskService.updateTask(id, title, desc, priority, status);
        System.out.println("Updated");
    }
}