import java.util.Scanner;

public class ArrayTask {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }

        int min = arr[0];
        int max = arr[0];
        int sum = 0;
        int even = 0;
        int odd = 0;

        for (int i = 0; i < n; i++) {
            if (arr[i] < min) min = arr[i];
            if (arr[i] > max) max = arr[i];

            sum += arr[i];

            if (arr[i] % 2 == 0) even++;
            else odd++;
        }

        double avg = (double) sum / n;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        System.out.println(min);
        System.out.println(max);
        System.out.println(avg);
        System.out.println(sum);

        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();

        System.out.println(even);
        System.out.println(odd);

        scanner.close();
    }
}