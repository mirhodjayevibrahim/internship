package com.internship.week6.task2.service;

import com.internship.week6.task1.dto.TaskResponseDto;
import com.internship.week6.task1.service.TaskService;
import com.internship.week6.task2.dto.CategoryCreateDto;
import com.internship.week6.task2.dto.CategoryResponseDto;
import com.internship.week6.task2.dto.CategoryUpdateDto;
import com.internship.week6.task2.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final List<Category> categories = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);
    private final TaskService taskService;

    public CategoryService(TaskService taskService) {
        this.taskService = taskService;
    }

    public List<CategoryResponseDto> getAllCategories() {
        return categories.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public CategoryResponseDto getCategoryById(Long id) {
        Category category = findCategoryOrThrow(id);
        return toResponseDto(category);
    }

    public CategoryResponseDto createCategory(CategoryCreateDto dto) {
        Category category = new Category();
        category.setId(counter.getAndIncrement());
        category.setName(dto.getName());
        category.setColor(dto.getColor());
        categories.add(category);
        return toResponseDto(category);
    }

    public CategoryResponseDto updateCategory(Long id, CategoryUpdateDto dto) {
        Category category = findCategoryOrThrow(id);
        if (dto.getName() != null) {
            category.setName(dto.getName());
        }
        if (dto.getColor() != null) {
            category.setColor(dto.getColor());
        }
        return toResponseDto(category);
    }

    public void deleteCategory(Long id) {
        Category category = findCategoryOrThrow(id);
        categories.remove(category);
    }

    public List<TaskResponseDto> getCategoryTasks(Long categoryId) {
        findCategoryOrThrow(categoryId);
        return taskService.getAllTasks(null, 0, Integer.MAX_VALUE);
    }

    private Category findCategoryOrThrow(Long id) {
        return categories.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    private CategoryResponseDto toResponseDto(Category category) {
        return new CategoryResponseDto(
                category.getId(),
                category.getName(),
                category.getColor()
        );
    }
}
