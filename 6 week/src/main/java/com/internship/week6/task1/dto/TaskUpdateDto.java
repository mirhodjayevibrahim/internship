package com.internship.week6.task1.dto;

import com.internship.week6.task1.model.TaskStatus;

import java.time.LocalDate;

public class TaskUpdateDto {
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate dueDate;

    public TaskUpdateDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
