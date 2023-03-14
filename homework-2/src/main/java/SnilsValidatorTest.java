import java.util.Scanner;

public class SnilsValidatorTest {

    public static void main(String[] args) {
        SnilsValidatorImpl snilsValidator = new SnilsValidatorImpl();
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите номер СНИЛС для проверки валидности: ");
            snilsValidator.validate(scanner.nextLine());
        }
    }
}