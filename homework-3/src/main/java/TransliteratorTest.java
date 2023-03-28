import java.util.Scanner;

public class TransliteratorTest {

    public static void main(String[] args) {
        Transliterator transliterator = new TransliteratorImpl();
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите предложение: ");
            System.out.println(transliterator.transliterate(scanner.nextLine()));
        }
    }
}