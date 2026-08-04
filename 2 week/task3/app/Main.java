package app;

import zoo.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Lion("Simba"));
        animals.add(new Dolphin("Flipper"));
        animals.add(new Eagle("Eddie"));

        System.out.println("=== All animals ===");
        for (Animal a : animals) {
            System.out.println(a);
        }

        System.out.println("\n=== Feeding Feedable ===");
        for (Animal a : animals) {
            if (a instanceof Feedable) {
                ((Feedable) a).feed();
            }
        }

        System.out.println("\n=== Training Trainable ===");
        for (Animal a : animals) {
            if (a instanceof Trainable) {
                ((Trainable) a).train();
            }
        }
    }
}
