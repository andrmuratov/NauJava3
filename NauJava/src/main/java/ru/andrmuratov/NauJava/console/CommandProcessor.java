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
        
        String result;
        try {
            result = switch (cmd) {
                case "create" -> handleCreate();
                case "list" -> handleList();
                case "find" -> handleFind(parts);
                case "delete" -> handleDelete(parts);
                case "update" -> handleUpdate(parts);
                default -> "Unknown command";
            };
        } catch (Exception e) {
            result = "Error: " + e.getMessage();
        }
        
        if (result != null && !result.isEmpty()) {
            System.out.println(result);
        }
    }

    private String handleCreate() {
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Description: ");
        String desc = scanner.nextLine();
        System.out.print("Priority (LOW/MEDIUM/HIGH): ");
        Task.Priority priority = Task.Priority.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Status (TODO/IN_PROGRESS/DONE): ");
        Task.Status status = Task.Status.valueOf(scanner.nextLine().toUpperCase());
        
        taskService.createTask(title, desc, priority, status);
        return "Task created";
    }

    private String handleList() {
        List<Task> tasks = taskService.findAll();
        if (tasks.isEmpty()) {
            return "No tasks found";
        }
        StringBuilder sb = new StringBuilder();
        for (Task task : tasks) {
            sb.append(task).append("\n");
        }
        return sb.toString().trim();
    }

    private String handleFind(String[] parts) {
        Long id = Long.valueOf(parts[1]);
        Task task = taskService.findById(id);
        return task != null ? task.toString() : "Not found";
    }

    private String handleDelete(String[] parts) {
        Long id = Long.valueOf(parts[1]);
        taskService.deleteById(id);
        return "Deleted";
    }

    private String handleUpdate(String[] parts) {
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
        return "Updated";
    }
}
