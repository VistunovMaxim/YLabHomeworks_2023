import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class OrgStructureParserTest {
    public static void main(String[] args) throws IOException {
        String file;
        boolean isStop = true;
        int choice = 0;
        int variable;
        OrgStructureParserImpl orgStructureParser = new OrgStructureParserImpl();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Введите полный путь к файлу: ");
            file = scanner.next();
            Employee employee = orgStructureParser.parseStructure(new File(file));

            while (isStop) {
                System.out.println("\n\u001B[32m" + employee.getPosition() + " - " + employee.getName() + " \u001B[0m" +
                        "\nПодчинённые: ");
                variable = 1;

                for (Employee empl : employee.getSubordinate()) {
                    System.out.println(variable + ") \u001B[36m" + empl.getPosition() + " - " + empl.getName() +
                            " (кол-во под. - " + empl.getSubordinate().size() + ") \u001B[0m");
                    variable++;
                }

                System.out.println("\nВыберите сотрудника из списка подчинённых:\n(0 - перейти на уровень выше)\n" +
                        "(-1 - закончить просмотр");
                choice = scanner.nextInt();
                if (choice == -1) {
                    isStop = false;
                } else if (choice == 0) {
                    if (!(employee.getBossId() == null)) {
                        employee = employee.getBoss();
                    } else {
                        System.err.println("У ген. директора нету начальника");
                    }
                } else {
                    employee = employee.getSubordinate().get(choice - 1);
                }
            }
        }
    }
}