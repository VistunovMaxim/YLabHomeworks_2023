import java.util.Scanner;

public class ComplexNumbersTest {

    public static void main(String[] args) {
        int stop = 0;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите первое комплексное число \"x + yi\":\n" +
                    "(x - действительная часть, y - мнимая часть)\n");
            ComplexNumbers firstCompNum = new ComplexNumbers(scanner.nextInt(), scanner.nextInt());
            System.out.println("Введите второе комплексное число \"x + yi\" :");
            ComplexNumbers secondCompNum = new ComplexNumbers(scanner.nextInt(), scanner.nextInt());
            while (stop == 0) {
                System.out.println("Выберите арифметическую операцию: \n1) Сложение\n2) Вычитание\n3) Умножение\n" +
                        "4) Модуль (первого и второго компл. числа)\n5) ВЫХОД");
                switch (scanner.nextInt()) {
                    case (1) -> System.out.println(firstCompNum.sum(secondCompNum) + "\n");
                    case (2) -> System.out.println(firstCompNum.sub(secondCompNum) + "\n");
                    case (3) -> System.out.println(firstCompNum.mul(secondCompNum) + "\n");
                    case (4) -> System.out.println("Первое число - " + firstCompNum.abs() +
                            "\nВторое число - " + secondCompNum.abs() + "\n");
                    case (5) -> stop = 1;
                }
            }

        }
    }
}
