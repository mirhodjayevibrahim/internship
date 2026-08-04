import java.util.Scanner;

public class FizzBuzz {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int start = scanner.nextInt();
        int end = scanner.nextInt();
        int fizzDivisor = scanner.nextInt();
        int buzzDivisor = scanner.nextInt();

        for (int i = start; i <= end; i++) {
            boolean fizz = i % fizzDivisor == 0;
            boolean buzz = i % buzzDivisor == 0;

            if (fizz && buzz) {
                System.out.println("FizzBuzz");
            } else if (fizz) {
                System.out.println("Fizz");
            } else if (buzz) {
                System.out.println("Buzz");
            } else {
                System.out.println(i);
            }
        }

        scanner.close();
    }
}