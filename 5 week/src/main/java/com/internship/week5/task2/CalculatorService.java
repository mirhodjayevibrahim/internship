package com.internship.week5.task2;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class CalculatorService {

    private final Map<String, MathOperation> operations;

    public CalculatorService(Map<String, MathOperation> operations) {
        this.operations = operations;
    }

    public double calculate(String operationName, double a, double b) {
        MathOperation operation = operations.get(operationName);
        if (operation == null) {
            throw new IllegalArgumentException("Unknown operation: " + operationName);
        }
        return operation.calculate(a, b);
    }

    public Set<String> getAvailableOperations() {
        return operations.keySet();
    }
}
