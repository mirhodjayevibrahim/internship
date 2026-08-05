package com.internship.week6;

import com.internship.week6.task1.dto.TaskCreateDto;
import com.internship.week6.task1.dto.TaskResponseDto;
import com.internship.week6.task1.model.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createTaskReturns201() {
        TaskCreateDto dto = new TaskCreateDto();
        dto.setTitle("Test Task");
        dto.setDescription("Description");

        ResponseEntity<TaskResponseDto> response = restTemplate.postForEntity("/tasks", dto, TaskResponseDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Test Task", response.getBody().getTitle());
    }

    @Test
    void getTaskNotFoundReturns404() {
        ResponseEntity<String> response = restTemplate.getForEntity("/tasks/999", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
