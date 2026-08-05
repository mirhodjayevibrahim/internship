package com.internship.week5.task2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CalculatorServiceTest {

    @Autowired
    private CalculatorService calculatorService;

    @Test
    void add() {
        assertEquals(5.0, calculatorService.calculate("add", 2, 3));
    }

    @Test
    void subtract() {
        assertEquals(1.0, calculatorService.calculate("subtract", 3, 2));
    }

    @Test
    void multiply() {
        assertEquals(6.0, calculatorService.calculate("multiply", 2, 3));
    }

    @Test
    void divide() {
        assertEquals(2.5, calculatorService.calculate("divide", 5, 2));
    }

    @Test
    void divideByZeroThrows() {
        assertThrows(ArithmeticException.class, () ->
                calculatorService.calculate("divide", 5, 0));
    }

    @Test
    void unknownOperationThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                calculatorService.calculate("power", 2, 3));
    }
}
