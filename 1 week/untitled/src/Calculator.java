import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Calculator {
    private static final int MAX_HISTORY = 10;
    private static final List<String> HISTORY = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Консольный калькулятор");
        System.out.println("Поддерживаемые операции: +, -, *, /, %, sqrt, pow");
        System.out.println("Введите выражение или 'exit' для выхода.");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                String result = evaluate(input);
                System.out.println("Результат: " + result);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }

            printHistory();
        }

        System.out.println("До свидания!");
        scanner.close();
    }

    private static String evaluate(String input) {
        String[] tokens = input.split("\\s+");
        if (tokens.length == 0) {
            throw new IllegalArgumentException("пустой ввод");
        }

        String command = tokens[0].toLowerCase();

        switch (command) {
            case "+":
            case "-":
            case "*":
            case "/":
            case "%":
                if (tokens.length != 3) {
                    throw new IllegalArgumentException("используйте формат: число оператор число");
                }
                return evaluateBinary(command, tokens[1], tokens[2]);
            case "sqrt":
                if (tokens.length != 2) {
                    throw new IllegalArgumentException("используйте формат: sqrt число");
                }
                return evaluateUnarySqrt(tokens[1]);
            case "pow":
                if (tokens.length != 3) {
                    throw new IllegalArgumentException("используйте формат: pow число степень");
                }
                return evaluatePower(tokens[1], tokens[2]);
            default:
                throw new IllegalArgumentException("неизвестная команда");
        }
    }

    private static String evaluateBinary(String operator, String leftToken, String rightToken) {
        double left = parseNumber(leftToken);
        double right = parseNumber(rightToken);

        double result;
        switch (operator) {
            case "+":
                result = left + right;
                break;
            case "-":
                result = left - right;
                break;
            case "*":
                result = left * right;
                break;
            case "/":
                if (right == 0) {
                    addHistory("Ошибка: деление на ноль для " + formatNumber(left) + " / " + formatNumber(right));
                    throw new IllegalArgumentException("деление на ноль");
                }
                result = left / right;
                break;
            case "%":
                if (right == 0) {
                    addHistory("Ошибка: деление на ноль для " + formatNumber(left) + " % " + formatNumber(right));
                    throw new IllegalArgumentException("деление на ноль");
                }
                result = left % right;
                break;
            default:
                throw new IllegalArgumentException("неизвестная операция");
        }

        addHistory(formatNumber(left) + " " + operator + " " + formatNumber(right) + " = " + formatNumber(result));
        return formatNumber(result);
    }

    private static String evaluateUnarySqrt(String valueToken) {
        double value = parseNumber(valueToken);
        if (value < 0) {
            addHistory("Ошибка: sqrt из отрицательного числа для " + formatNumber(value));
            throw new IllegalArgumentException("sqrt из отрицательного числа");
        }

        double result = Math.sqrt(value);
        addHistory("sqrt(" + formatNumber(value) + ") = " + formatNumber(result));
        return formatNumber(result);
    }

    private static String evaluatePower(String baseToken, String exponentToken) {
        double base = parseNumber(baseToken);
        double exponent = parseNumber(exponentToken);
        double result = Math.pow(base, exponent);
        addHistory("pow(" + formatNumber(base) + ", " + formatNumber(exponent) + ") = " + formatNumber(result));
        return formatNumber(result);
    }

    private static double parseNumber(String token) {
        try {
            return Double.parseDouble(token);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("некорректное число: " + token);
        }
    }

    private static void addHistory(String entry) {
        if (HISTORY.size() == MAX_HISTORY) {
            HISTORY.remove(0);
        }
        HISTORY.add(entry);
    }

    private static void printHistory() {
        System.out.println("История последних 10 операций:");
        if (HISTORY.isEmpty()) {
            System.out.println("(пока пусто)");
        } else {
            for (String entry : HISTORY) {
                System.out.println("- " + entry);
            }
        }
    }

    private static String formatNumber(double value) {
        if (value == Math.rint(value)) {
            return String.format("%.0f", value);
        }
        return String.format("%.10f", value).replaceAll("0+$", "").replaceAll("\\.$", "");
    }
}
