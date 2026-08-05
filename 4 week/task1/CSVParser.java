import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CSVParser {

    static class User {
        int id;
        String name;
        String email;
        int age;

        User(int id, String name, String email, int age) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.age = age;
        }

        @Override
        public String toString() {
            return id + " | " + name + " | " + email + " | " + age;
        }
    }

    public static void main(String[] args) {
        String csvFile = "users.csv";
        String errorFile = "errors.log";

        List<User> validUsers = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(csvFile));
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл: " + csvFile);
            return;
        }

        if (lines.isEmpty()) {
            System.out.println("Файл пуст");
            return;
        }

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(",");
            if (parts.length != 4) {
                errors.add("Строка " + (i + 1) + ": неверное количество полей (ожидается 4, получено " + parts.length + ")");
                continue;
            }

            String reason = "";
            int id = -1;
            try {
                id = Integer.parseInt(parts[0].trim());
            } catch (NumberFormatException e) {
                reason = "невалидный id: '" + parts[0].trim() + "'";
            }

            String name = parts[1].trim();
            if (name.isEmpty()) {
                reason += (reason.isEmpty() ? "" : ", ") + "пустое имя";
            }

            String email = parts[2].trim();
            if (!email.contains("@")) {
                reason += (reason.isEmpty() ? "" : ", ") + "email не содержит @: '" + email + "'";
            }

            int age = -1;
            try {
                age = Integer.parseInt(parts[3].trim());
                if (age < 0 || age > 120) {
                    reason += (reason.isEmpty() ? "" : ", ") + "возраст вне диапазона 0-120: " + age;
                }
            } catch (NumberFormatException e) {
                reason += (reason.isEmpty() ? "" : ", ") + "невалидный возраст: '" + parts[3].trim() + "'";
            }

            if (!reason.isEmpty()) {
                errors.add("Строка " + (i + 1) + ": " + reason);
            } else {
                validUsers.add(new User(id, name, email, age));
            }
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(errorFile))) {
            for (String err : errors) {
                pw.println(err);
            }
        } catch (IOException e) {
            System.out.println("Не удалось записать errors.log");
        }

        System.out.println("=== Валидные записи (" + validUsers.size() + ") ===");
        for (User u : validUsers) {
            System.out.println(u);
        }

        System.out.println("\n=== Статистика ===");
        if (!validUsers.isEmpty()) {
            double avgAge = validUsers.stream().mapToInt(u -> u.age).average().orElse(0);
            int minAge = validUsers.stream().mapToInt(u -> u.age).min().orElse(0);
            int maxAge = validUsers.stream().mapToInt(u -> u.age).max().orElse(0);
            System.out.println("Всего валидных: " + validUsers.size());
            System.out.println("Средний возраст: " + String.format("%.1f", avgAge));
            System.out.println("Минимальный возраст: " + minAge);
            System.out.println("Максимальный возраст: " + maxAge);
        }

        System.out.println("\n=== Ошибки (" + errors.size() + ") ===");
        for (String err : errors) {
            System.out.println(err);
        }
        if (!errors.isEmpty()) {
            System.out.println("Ошибки записаны в " + errorFile);
        }
    }
}
