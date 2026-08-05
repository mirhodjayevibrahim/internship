package com.internship.week5.task2;

import org.springframework.stereotype.Component;

@Component("add")
public class AddOperation implements MathOperation {

    @Override
    public double calculate(double a, double b) {
        return a + b;
    }

    @Override
    public String getSymbol() {
        return "+";
    }
}
