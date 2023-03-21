public class PasswordValidator {

    public static final PasswordValidator INSTANCE = new PasswordValidator();
    private String login;
    private String password;

    private PasswordValidator(){}

    public void setLogin(String login) throws WrongLoginException {
        if (!login.matches("^[a-zA-Z0-9_]+$")) {
            throw new WrongLoginException("Логин содержит недопустимые символы");
        } else if (!(login.length() < 20)) {
            throw new WrongLoginException("Логин слишком длинный");
        } else {
            this.login = login;
        }
    }

    public void setPassword(String password, String confirmPassword) throws WrongPasswordException {
        if (!password.matches("^[a-zA-Z0-9_]+$")) {
            throw new WrongPasswordException("Пароль содержит недопустимые символы");
        } else if (!(password.length() < 20)) {
            throw new WrongPasswordException("Пароль слишком длинный");
        } else if (password.compareTo(confirmPassword) != 0) {
            throw new WrongPasswordException("Пароль и подтверждение не совпадают");
        } else {
            this.password = password;
        }
    }

    public static boolean passValidator(String login, String password, String confirmPassword) {
        try {
            INSTANCE.setLogin(login);
            INSTANCE.setPassword(password, confirmPassword);
        } catch (WrongLoginException | WrongPasswordException e) {
            System.err.println(e.getMessage());
            return false;
        }

        return true;
    }

}
