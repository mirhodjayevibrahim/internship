package app;

import shapes.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Circle(5));
        shapes.add(new Rectangle(4, 6));
        shapes.add(new Triangle(3, 4, 5));
        shapes.add(new Square(7));

        System.out.println("=== All shapes ===");
        for (Shape s : shapes) {
            System.out.println(s + " -> area = " + String.format("%.2f", s.area()));
        }

        Shape max = shapes.get(0);
        for (Shape s : shapes) {
            if (s.area() > max.area()) {
                max = s;
            }
        }
        System.out.println("\nMax area: " + max + " -> " + String.format("%.2f", max.area()));

        double total = 0;
        for (Shape s : shapes) {
            total += s.area();
        }
        System.out.println("Total area: " + String.format("%.2f", total));
    }
}
