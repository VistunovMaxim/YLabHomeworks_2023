import java.util.Random;
import java.util.Scanner;

public class HomeWork {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            HomeWork solution = new HomeWork();

            System.out.println("1) Stars\n2) Pell\n3) MultTable\n4) Guess\nВведите номер задачи: ");

            switch (scanner.nextInt()) {
                case 1 -> solution.stars();
                case 2 -> solution.pell();
                case 3 -> solution.multTable();
                case 4 -> solution.guess();
            }
        }
    }

    public void stars() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите кол-во строк, кол-во столбцов и символ для заполнения: ");
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            String template = scanner.next();

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    System.out.print(template);
                }
                System.out.println();
            }
        }
    }

    public void pell() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите порядковый номер числа Пелля:");
            int n = scanner.nextInt();
            int x = 0;
            int y = 1;
            int result = 0;
            if (n == 0) {
                System.out.println(x);
            } else if (n == 1) {
                System.out.println(y);
            } else {
                for (int i = 2; i <= n; i++) {
                    result = 2 * y + x;
                    x = y;
                    y = result;
                }
                System.out.println(result);
            }
        }
    }

    public void multTable() {
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                System.out.println(i + " x " + j + " = " + i * j);
            }
        }
    }

    public void guess() {
        try (Scanner scanner = new Scanner(System.in)) {
            int number = new Random().nextInt(99) + 1;
            int maxAttempts = 10;
            int variable;
            System.out.println("Я загадал число. У тебя " + maxAttempts + " попыток угадать.");

            for (int i = 1; i <= maxAttempts; i++) {

                variable = scanner.nextInt();

                if (variable == number) {
                    System.out.println("Ты угадал с " + i + " попытки");
                    break;
                } else if (variable > number) {
                    System.out.print("Мое число меньше!");
                } else {
                    System.out.print("Мое число больше!");
                }

                System.out.println(10 - i == 0 ? " У тебя осталось 0 попыток\nТы не угадал" :
                        " У тебя осталось " + (10 - i) + " попыток");
            }
        }
    }

}