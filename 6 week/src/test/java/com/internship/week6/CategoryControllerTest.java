package com.internship.week6;

import com.internship.week6.task2.dto.CategoryCreateDto;
import com.internship.week6.task2.dto.CategoryResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createCategoryReturns201() {
        CategoryCreateDto dto = new CategoryCreateDto();
        dto.setName("Work");
        dto.setColor("#FF0000");

        ResponseEntity<CategoryResponseDto> response = restTemplate.postForEntity("/categories", dto, CategoryResponseDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Work", response.getBody().getName());
    }

    @Test
    void getCategoryNotFoundReturns404() {
        ResponseEntity<String> response = restTemplate.getForEntity("/categories/999", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
