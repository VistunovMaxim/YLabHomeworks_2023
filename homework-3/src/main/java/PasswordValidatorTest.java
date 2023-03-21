import java.util.Scanner;

public class PasswordValidatorTest {
    public static void main(String[] args) {

        String login = "-";
        String password = "-";
        String confirmPassword = "-";
        boolean successAuthentication = false;

        try (Scanner scanner = new Scanner(System.in)) {

            while (!successAuthentication) {
                System.out.print("Введите логин: ");
                login = scanner.next();
                System.out.print("Введите пароль: ");
                password = scanner.next();
                System.out.print("Подтвердите пароль: ");
                confirmPassword = scanner.next();
                successAuthentication = PasswordValidator.passValidator(login, password, confirmPassword);
                Thread.sleep(100);
                System.out.println(successAuthentication ? "\u001B[32m" + "Успешная регистрация" + "\u001B[0m": "");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
