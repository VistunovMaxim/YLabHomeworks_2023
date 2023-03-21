import java.util.Scanner;

public class SequencesTest {

    public static void main(String[] args) {
        SequencesImpl sequences = new SequencesImpl();
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Выберите последовательность от A до J (ALL - для вывода всех послед.): ");
            String sequenceNum = scanner.next().toUpperCase();
            System.out.println("Введите кол-во элементов для вывода: ");
            int n = scanner.nextInt();
            if (n < 0)
                System.out.println("Кол-во элементов для вывода не может быть отрицательным");
            else {
                switch (sequenceNum) {
                    case ("A") -> sequences.a(n);
                    case ("B") -> sequences.b(n);
                    case ("C") -> sequences.c(n);
                    case ("D") -> sequences.d(n);
                    case ("E") -> sequences.e(n);
                    case ("F") -> sequences.f(n);
                    case ("G") -> sequences.g(n);
                    case ("H") -> sequences.h(n);
                    case ("I") -> sequences.i(n);
                    case ("J") -> sequences.j(n);
                    case ("ALL") -> {
                        sequences.a(n);
                        sequences.b(n);
                        sequences.c(n);
                        sequences.d(n);
                        sequences.e(n);
                        sequences.f(n);
                        sequences.g(n);
                        sequences.h(n);
                        sequences.i(n);
                        sequences.j(n);
                    }
                    default -> System.out.println("Несуществующая последовательность");
                }
            }
        }
    }
}
