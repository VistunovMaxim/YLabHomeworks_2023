public class SnilsValidatorImpl implements SnilsValidator {

    @Override
    public boolean validate(String snils) {

        int sum = 0;
        int checkNumber;

        snils = snils.replaceAll(" ", "");
        snils = snils.replaceAll("-", "");

        if (snils.length() != 11) {
            System.out.println("СНИЛС может состоять только из 11-ти цифр");
            return false;
        }

        for (int i = 0; i < snils.length(); i++) {
            if (!Character.isDigit(snils.charAt(i))) {
                System.out.println("СНИЛС может состоять только из цифр");
                return false;
            }
        }

        checkNumber = Integer.parseInt(snils.substring(9, 11));

        for (int i = 0; i < 9; i++) {
            sum += Character.digit(snils.charAt(i), 10) * (9 - i);
        }

        if (sum < 100 && checkNumber == sum) {
            System.out.println("СНИЛС корректный");
            return true;
        } else if (sum == 100 && checkNumber == 0) {
            System.out.println("СНИЛС корректный");
            return true;
        } else {
            if (sum % 101 == 100 && checkNumber == 0) {
                System.out.println("СНИЛС корректный");
                return true;
            } else if (sum % 101 == checkNumber) {
                System.out.println("СНИЛС корректный");
                return true;
            }
        }
        System.out.println("СНИЛС некорректный");
        return false;
    }
}
