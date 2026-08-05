package com.internship.week6.task1.service;

import com.internship.week6.task1.dto.TaskCreateDto;
import com.internship.week6.task1.dto.TaskResponseDto;
import com.internship.week6.task1.dto.TaskUpdateDto;
import com.internship.week6.task1.model.Task;
import com.internship.week6.task1.model.TaskStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final List<Task> tasks = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    public List<TaskResponseDto> getAllTasks(TaskStatus status, int page, int size) {
        return tasks.stream()
                .filter(t -> status == null || t.getStatus() == status)
                .skip((long) page * size)
                .limit(size)
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public TaskResponseDto getTaskById(Long id) {
        Task task = findTaskOrThrow(id);
        return toResponseDto(task);
    }

    public TaskResponseDto createTask(TaskCreateDto dto) {
        Task task = new Task();
        task.setId(counter.getAndIncrement());
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(TaskStatus.TODO);
        task.setCreatedAt(LocalDateTime.now());
        task.setDueDate(dto.getDueDate());
        tasks.add(task);
        return toResponseDto(task);
    }

    public TaskResponseDto updateTask(Long id, TaskUpdateDto dto) {
        Task task = findTaskOrThrow(id);
        if (dto.getTitle() != null) {
            task.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            task.setDescription(dto.getDescription());
        }
        if (dto.getStatus() != null) {
            task.setStatus(dto.getStatus());
        }
        if (dto.getDueDate() != null) {
            task.setDueDate(dto.getDueDate());
        }
        return toResponseDto(task);
    }

    public void deleteTask(Long id) {
        Task task = findTaskOrThrow(id);
        tasks.remove(task);
    }

    private Task findTaskOrThrow(Long id) {
        return tasks.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    private TaskResponseDto toResponseDto(Task task) {
        return new TaskResponseDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getDueDate()
        );
    }
}
