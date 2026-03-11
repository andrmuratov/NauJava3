package ru.andrmuratov.NauJava.entity;

import java.time.LocalDateTime;

public class Task {
    private Long id;
    private String title;
    private String description;
    private Priority priority;
    private Status status;
    private LocalDateTime deadline;


    public Task() {}


    public Task(Long id, String title, String description, Priority priority, Status status, LocalDateTime deadline) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.deadline = deadline;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getDeadline() { return deadline; }
    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }


    public enum Priority {
        LOW, MEDIUM, HIGH
    }


    public enum Status {
        TODO, IN_PROGRESS, DONE
    }


    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", priority=" + priority +
                ", status=" + status +
                ", deadline=" + deadline +
                '}';
    }
}